package com.dental.clinic.dao;

import com.dental.clinic.model.Patient;
import com.dental.clinic.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public int save (Patient patient){
        String sql = "INSERT INTO patients (name, address, contact_number) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString (1, patient.getName());
            stmt.setString (2, patient.getAddress());
            stmt.setString (3, patient.getContactNumber());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()){
                return keys.getInt(1);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Falid to save patient", e);
        }
        return -1;
    }

    @Override
    public Patient findById (int patientId){
        String sql = "SELECT * FORM patients WHERE patient_id = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapRow(rs);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to find patient", e);
        }
        return null;
    }

    private Patient mapRow(ResultSet rs) throws SQLException{
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patient_id"));
        patient.setName(rs.getString("name"));
        patient.setAddress(rs.getString("address"));
        patient.setContactNumber(rs.getString("contact_number"));
        return patient;
    }

}
