package com.dental.clinic.dao;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.Patient;
import com.dental.clinic.model.TreatmentType;
import com.dental.clinic.util.DBConnection;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class BillDAOImpl implements BillDAO{

    @Override
    public void save(Bill bill) {
        String sql = "INSERT INTO bills " +
                "(bill_id, appointment_number, consultation_fee, treatment_cost, total_amount,bill_date) " +
                "VALUES (?, ?, ?, ?, ?,?)";

        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, bill.getBillId());
            stmt.setString(2, bill.getAppointment().getAppointmentNumber());
            stmt.setDouble(3, bill.getConsultationFee());
            stmt.setDouble(4, bill.getTreatmentCost());
            stmt.setDouble(5, bill.getTotalAmount());
            stmt.setDate(6, Date.valueOf(bill.getBillDate()));
            stmt.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to save this bill", e);
        }
    }

    @Override
    public Bill findByAppointmentNumber(String appointmentNumber) {
        String sql = "SELECT b.*, " +
                "a.appointment_number, a.appointment_date, a.appointment_time, a.status, " +
                "p.patient_id, p.name AS patient_name, p.address AS patient_address, p.contact_number AS patient_contact, " +
                "d.dentist_id, d.name AS dentist_name, d.specialization AS dentist_specialization, d.consultation_fee, " +
                "t.treatment_id, t.treatment_name, t.cost AS treatment_cost " +
                "FROM bills b " +
                "JOIN appointments a ON b.appointment_number = a.appointment_number " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                "JOIN treatment_types t ON a.treatment_id = t.treatment_id " +
                "WHERE b.appointment_number = ?";

        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, appointmentNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapRow(rs);
            }

        }
        catch (SQLException e) {
            throw new RuntimeException("Failed to find bill", e);
        }
        return null;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM bills";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to count bills", e);
        }
        return 0;
    }

    @Override
    public List<Bill> findAll() {

        String sql = "SELECT b.*, " +
                "a.appointment_number, a.appointment_date, a.appointment_time, a.status, " +
                "p.patient_id, p.name AS patient_name, p.address AS patient_address, p.contact_number AS patient_contact, " +
                "d.dentist_id, d.name AS dentist_name, d.specialization AS dentist_specialization, d.consultation_fee, " +
                "t.treatment_id, t.treatment_name, t.cost AS treatment_cost " +
                "FROM bills b " +
                "JOIN appointments a ON b.appointment_number = a.appointment_number " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN dentists d ON a.dentist_id = d.dentist_id " +
                "JOIN treatment_types t ON a.treatment_id = t.treatment_id " +
                "ORDER BY b.bill_date DESC";

        List<Bill> bills = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bills.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch bills", e);
        }
        return bills;
    }

    private Bill mapRow(ResultSet rs) throws SQLException {
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

        TreatmentType treatmentType = new TreatmentType();
        treatmentType.setTreatmentId(rs.getInt("treatment_id"));
        treatmentType.setTreatmentName(rs.getString("treatment_name"));
        treatmentType.setCost(rs.getDouble("treatment_cost"));

        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber(rs.getString("appointment_number"));
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatmentType(treatmentType);
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
}
