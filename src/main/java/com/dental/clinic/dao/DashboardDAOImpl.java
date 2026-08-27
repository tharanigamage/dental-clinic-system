package com.dental.clinic.dao;

import com.dental.clinic.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Map<String, Double> getMonthlyRevenueTrend(int monthsBack) {

        Map<String, Double> trend = new LinkedHashMap<>();
        DateTimeFormatter labelFormat = DateTimeFormatter.ofPattern("MMM yyyy");

        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM bills " +
                "WHERE MONTH(bill_date) = ? AND YEAR(bill_date) = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        // Walk backwards from (monthsBack - 1) months ago up to the current month
        for (int i = monthsBack - 1; i >= 0; i--) {
            LocalDate targetMonth = LocalDate.now().minusMonths(i);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, targetMonth.getMonthValue());
                stmt.setInt(2, targetMonth.getYear());

                ResultSet rs = stmt.executeQuery();
                double revenue = 0;
                if (rs.next()) {
                    revenue = rs.getDouble(1);
                }

                trend.put(targetMonth.format(labelFormat), revenue);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to fetch monthly revenue trend", e);
            }
        }

        return trend;
    }

    @Override
    public List<Double> getDailyRevenueThisMonth() {
        List<Double> dailyRevenue = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int daysSoFar = today.getDayOfMonth();

        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM bills WHERE bill_date = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        for (int day = 1; day <= daysSoFar; day++) {
            LocalDate targetDay = today.withDayOfMonth(day);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, Date.valueOf(targetDay));
                ResultSet rs = stmt.executeQuery();
                double revenue = 0;
                if (rs.next()) {
                    revenue = rs.getDouble(1);
                }
                dailyRevenue.add(revenue);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to fetch daily revenue for sparkline", e);
            }
        }

        return dailyRevenue;
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

    @Override
    public Map<String, Integer> getTopTreatments(int limit) {
        // LinkedHashMap preserves order, so the chart draws bars highest-to-lowest,
        // matching the ORDER BY ... DESC in the SQL query.
        Map<String, Integer> topTreatments = new LinkedHashMap<>();

        String sql = "SELECT t.treatment_name, COUNT(*) AS booking_count " +
                "FROM appointments a " +
                "JOIN treatment_types t ON a.treatment_id = t.treatment_id " +
                "GROUP BY t.treatment_name " +
                "ORDER BY booking_count DESC " +
                "LIMIT ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                topTreatments.put(rs.getString("treatment_name"), rs.getInt("booking_count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch top treatments", e);
        }

        return topTreatments;
    }
}
