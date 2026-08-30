package com.dental.clinic.dao;

import com.dental.clinic.model.Dentist;
import com.dental.clinic.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DentistDAOImpl implements DentistDAO{
    // All dentists
    @Override
    public List<Dentist> findAll(){
        String sql = "SELECT * FROM dentists";
        List<Dentist> dentists = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                dentists.add(mapRow(rs));
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to fetch dentists", e);
        }
        return dentists;
    }

    //Find by id
    @Override
    public Dentist findById (int dentistId){
        String sql = "SELECT * FROM dentists WHERE dentist_id = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, dentistId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapRow(rs);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to find dentist", e);
        }
        return null;
    }

    // Convert database result to object
    private Dentist mapRow (ResultSet rs) throws SQLException{
        Dentist dentist = new Dentist();
        dentist.setDentistId(rs.getInt("dentist_id"));
        dentist.setName(rs.getString("name"));
        dentist.setSpecialization(rs.getString("specialization"));
        dentist.setConsultationFee(rs.getDouble("consultation_fee"));
        return dentist;
    }
}
