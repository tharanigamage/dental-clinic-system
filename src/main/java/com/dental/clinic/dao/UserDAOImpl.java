package com.dental.clinic.dao;

import com.dental.clinic.model.User;
import com.dental.clinic.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findByUsername (String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return mapRow(rs);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to find user", e);
        }
        return null;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_Id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    }
}
