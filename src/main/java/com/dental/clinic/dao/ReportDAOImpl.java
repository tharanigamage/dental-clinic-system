package com.dental.clinic.dao;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.Patient;
import com.dental.clinic.model.TreatmentType;
import com.dental.clinic.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDAOImpl implements ReportDAO{

    // Find appointments by date
    @Override
    public List<Appointment> findAppointmentsByDate(LocalDate date) {
        String sql = "SELECT a.*, " +
                "p.name AS patient_name, p.address AS patient_address, p.contact_number AS patient_contact, " +
                "d.name AS dentist_name, d.specialization AS dentist_specialization, d.consultation_fee " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                "WHERE a.appointment_date = ? " +
                "ORDER BY a.appointment_time ASC";

        List<Appointment> appointments = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch daily appointments report", e);
        }
        return appointments;
    }

    // Treatment details with comma
    private List<TreatmentType> fetchTreatmentsByCsv(String csv) {
        List<TreatmentType> result = new ArrayList<>();
        if (csv == null || csv.isBlank()) {
            return result;
        }

        String[] ids = csv.split(",");
        String sql = "SELECT * FROM treatment_types WHERE treatment_id = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        for (String idStr : ids) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(idStr.trim()));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    TreatmentType t = new TreatmentType();
                    t.setTreatmentId(rs.getInt("treatment_id"));
                    t.setTreatmentName(rs.getString("treatment_name"));
                    t.setCost(rs.getDouble("cost"));
                    result.add(t);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to fetch treatment by ID", e);
            }
        }
        return result;
    }

    // Convert database result to object
    private Appointment mapRow(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patient_id"));
        patient.setName(rs.getString("patient_name"));
        patient.setAddress(rs.getString("patient_address"));
        patient.setContactNumber(rs.getString("patient_contact"));

        Dentist dentist = new Dentist();
        dentist.setDentistId(rs.getInt("dentist_id"));
        dentist.setName(rs.getString("dentist_name"));
        dentist.setSpecialization(rs.getString("dentist_specialization"));
        dentist.setConsultationFee(rs.getDouble("consultation_fee"));

        List<TreatmentType> treatmentTypes = fetchTreatmentsByCsv(rs.getString("treatment_id"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber(rs.getString("appointment_number"));
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatmentTypes(treatmentTypes);
        appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
        appointment.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
        appointment.setStatus(rs.getString("status"));

        return appointment;
    }

    // Find bills for a select month and year
    @Override
    public List<Bill> findBillsByMonth(int month, int year) {
        String sql = "SELECT b.*, " +
                "a.appointment_number, a.appointment_date, a.appointment_time, a.status, a.treatment_id, " +
                "p.patient_id, p.name AS patient_name, p.address AS patient_address, p.contact_number AS patient_contact, " +
                "d.dentist_id, d.name AS dentist_name, d.specialization AS dentist_specialization, d.consultation_fee " +
                "FROM bills b " +
                "JOIN appointments a ON b.appointment_number = a.appointment_number " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                "WHERE MONTH(b.bill_date) = ? AND YEAR(b.bill_date) = ? " +
                "ORDER BY b.bill_date ASC";

        List<Bill> bills = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bills.add(mapBillRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch monthly revenue report", e);
        }
        return bills;
    }

    // Convert database result to object
    private Bill mapBillRow(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patient_id"));
        patient.setName(rs.getString("patient_name"));
        patient.setAddress(rs.getString("patient_address"));
        patient.setContactNumber(rs.getString("patient_contact"));

        Dentist dentist = new Dentist();
        dentist.setDentistId(rs.getInt("dentist_id"));
        dentist.setName(rs.getString("dentist_name"));
        dentist.setSpecialization(rs.getString("dentist_specialization"));
        dentist.setConsultationFee(rs.getDouble("consultation_fee"));

        List<TreatmentType> treatmentTypes = fetchTreatmentsByCsv(rs.getString("treatment_id"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber(rs.getString("appointment_number"));
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatmentTypes(treatmentTypes);
        appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
        appointment.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
        appointment.setStatus(rs.getString("status"));

        Bill bill = new Bill();
        bill.setBillId(rs.getString("bill_id"));
        bill.setAppointment(appointment);
        bill.setConsultationFee(rs.getDouble("consultation_fee"));
        bill.setTreatmentCost(rs.getDouble("treatment_cost"));
        bill.setTotalAmount(rs.getDouble("total_amount"));
        bill.setBillDate(rs.getDate("bill_date").toLocalDate());

        return bill;
    }

    // Find appointment by dentist and date range
    @Override
    public List<Appointment> findAppointmentsByDentistAndDateRange(int dentistId, LocalDate from, LocalDate to) {
        String sql = "SELECT a.*, " +
                "p.name AS patient_name, p.address AS patient_address, p.contact_number AS patient_contact, " +
                "d.name AS dentist_name, d.specialization AS dentist_specialization, d.consultation_fee " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                "WHERE a.dentist_id = ? AND a.appointment_date BETWEEN ? AND ? " +
                "ORDER BY a.appointment_date ASC, a.appointment_time ASC";

        List<Appointment> appointments = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dentistId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch dentist-wise appointment report", e);
        }
        return appointments;
    }

}