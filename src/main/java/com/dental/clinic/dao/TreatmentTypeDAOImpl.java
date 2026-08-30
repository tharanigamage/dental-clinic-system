package com.dental.clinic.dao;

import com.dental.clinic.model.TreatmentType;
import com.dental.clinic.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentTypeDAOImpl implements TreatmentTypeDAO{

    // Find all treatment types
    @Override
    public List<TreatmentType> findAll (){
        String sql = "SELECT * FROM treatment_types";
        List<TreatmentType> list = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                list.add(mapRow(rs));
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Falied to fetch treatment type", e);
        }
        return list;
    }

    // Find by id
    @Override
    public TreatmentType findById (int treatmentId){
        String sql = "SELECT * FROM treatment_types WHERE treatment_id = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,treatmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapRow(rs);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to find treatment type", e);
        }
        return  null;
    }

    // Convert database result to object
    private TreatmentType mapRow(ResultSet rs) throws SQLException{
        TreatmentType t = new TreatmentType();
        t.setTreatmentId(rs.getInt("treatment_id"));
        t.setTreatmentName(rs.getString("treatment_name"));
        t.setCost(rs.getDouble("cost"));
        return t;
    }
}
