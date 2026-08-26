package com.dental.clinic.dao;

import com.dental.clinic.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAOImpl implements DashboardDAO {

    @Override
    public int countAppointmentsToday() {
        String sql = "SELECT COUNT(*) FROM appointments WHERE appointment_date = CURDATE()";
        return runCountQuery(sql);
    }

    @Override
    public int countAppointmentsThisMonth() {
        String sql = "SELECT COUNT(*) FROM appointments " +
                "WHERE MONTH(appointment_date) = MONTH(CURDATE()) " +
                "AND YEAR(appointment_date) = YEAR(CURDATE())";
        return runCountQuery(sql);
    }

    @Override
    public double sumRevenueThisMonth() {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM bills " +
                "WHERE MONTH(bill_date) = MONTH(CURDATE()) " +
                "AND YEAR(bill_date) = YEAR(CURDATE())";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to calculate monthly revenue", e);
        }
        return 0;
    }

    @Override
    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE status = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to count appointments by status", e);
        }
        return 0;
    }

    @Override
    public String findMostBookedTreatmentName() {
        String sql = "SELECT t.treatment_name, COUNT(*) AS booking_count " +
                "FROM appointments a " +
                "JOIN treatment_types t ON a.treatment_id = t.treatment_id " +
                "GROUP BY t.treatment_name " +
                "ORDER BY booking_count DESC " +
                "LIMIT 1";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("treatment_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find most booked treatment", e);
        }
        return "N/A";
    }

    private int runCountQuery(String sql) {
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to run count query", e);
        }
        return 0;
    }
}
