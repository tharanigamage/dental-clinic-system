package com.dental.clinic.dao;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.Patient;
import com.dental.clinic.model.TreatmentType;
import com.dental.clinic.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;

public class AppointmentDAOImpl implements AppointmentDAO{

    private static final String JOIN_QUERY =
            "SELECT a.*, " +
                    "p.name AS patient_name, p.address AS patient_address, p.contact_number AS patient_contact, " +
            "d.name AS dentist_name, d.specialization AS dentist_specialization, d.consultation_fee, " +
            "t.treatment_name, t.cost AS treatment_cost " +
            "FROM appointments a "+
            "JOIN patients p ON a.patient_id = p.patient_id " +
            "JOIN dentists d ON a.dentist_id = d.dentist_id " +
            "JOIN treatment_types t ON a.treatment_id = t.treatment_id ";

    @Override
    public void save(Appointment appointment){
        String sql = "INSERT INTO appointments "+
                "(appointment_number, patient_id, dentist_id, treatment_id, appointment_date, appointment_time,status) "+
                "VALUES(?,?,?,?,?,?,?)";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,appointment.getAppointmentNumber());
            stmt.setInt(2, appointment.getPatient().getPatientId());
            stmt.setInt(3, appointment.getDentist().getDentistId());
            stmt.setInt(4, appointment.getTreatmentType().getTreatmentId());
            stmt.setDate(5, Date.valueOf (appointment.getAppointmentDate()));
            stmt.setTime(6, Time.valueOf (appointment.getAppointmentTime()));
            stmt.setString(7, appointment.getStatus());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to save appointment", e);
        }
    }
    @Override
    public Appointment findByAppointmentNumber (String appointmentNumber){
        String sql = JOIN_QUERY+ "WHERE a.appointment_number = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,appointmentNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapRow(rs);
            }
        }catch (SQLException e){
            throw new RuntimeException("Failed to find appointment", e);
        }
        return null;
    }

    @Override
    public List<Appointment> findAll(){
        String sql = JOIN_QUERY + "ORDER BY a.appointment_date DESC, a.appointment_time DESC";
        List<Appointment>appointments = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                appointments.add(mapRow(rs));
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to fetch appointments", e);
        }
        return appointments;
    }

    @Override
    public void updateStatus (String appointmentNumber, String status){
        String sql = "UPDATE appointments SET status = ? WHERE appointment_number = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, status);
            stmt.setString(2, appointmentNumber);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to update appointment status", e);
        }
    }

    @Override
    public void updateAppointment(String appointmentNumber, LocalDate date, LocalTime time, String status) {
        String sql = "UPDATE appointments SET appointment_date = ?, appointment_time = ?, status = ? " +
                "WHERE appointment_number = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            stmt.setTime(2, Time.valueOf(time));
            stmt.setString(3, status);
            stmt.setString(4, appointmentNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update appointment", e);
        }
    }

    @Override
    public int countAll(){
        String sql = "SELECT COUNT(*) FROM appointments";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to count appointment", e);
        }
        return 0;
    }

    @Override
    public boolean existsByDentistDateTime(int dentistId, LocalDate date, LocalTime time) {
        String sql = "SELECT COUNT(*) FROM appointments " +
                "WHERE dentist_id = ? AND appointment_date = ? AND appointment_time = ? " +
                "AND status != 'Cancelled'";

        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, dentistId);
            stmt.setDate(2, Date.valueOf(date));
            stmt.setTime(3, Time.valueOf(time));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt(1) > 0;
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to check for duplicate appointment", e);
        }
        return false;
    }

    private Appointment mapRow (ResultSet rs)throws SQLException{
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

        return appointment;
    }
}
