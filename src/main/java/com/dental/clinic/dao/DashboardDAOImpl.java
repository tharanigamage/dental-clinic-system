package com.dental.clinic.dao;

import com.dental.clinic.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashboardDAOImpl implements DashboardDAO {

    //Today appointment count
    @Override
    public int countAppointmentsToday() {
        String sql = "SELECT COUNT(*) FROM appointments WHERE appointment_date = CURDATE()";
        return runCountQuery(sql);
    }

    //Count appointment this month
    @Override
    public int countAppointmentsThisMonth() {
        String sql = "SELECT COUNT(*) FROM appointments " +
                "WHERE MONTH(appointment_date) = MONTH(CURDATE()) " +
                "AND YEAR(appointment_date) = YEAR(CURDATE())";
        return runCountQuery(sql);
    }

    //Total revenue this month
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

    // Status count
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

    // Find most booked treatment name
    @Override
    public String findMostBookedTreatmentName() {
        Map<Integer, Integer> counts = countTreatmentOccurrences();
        if (counts.isEmpty()) {
            return "N/A";
        }

        int topId = -1;
        int topCount = -1;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > topCount) {
                topCount = entry.getValue();
                topId = entry.getKey();
            }
        }
        return getTreatmentNameById(topId);
    }

    //Get top treatments
    @Override
    public Map<String, Integer> getTopTreatments(int limit) {
        Map<Integer, Integer> counts = countTreatmentOccurrences();

        List<Map.Entry<Integer, Integer>> sorted = new ArrayList<>(counts.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        Map<String, Integer> topTreatments = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(limit, sorted.size()); i++) {
            Map.Entry<Integer, Integer> entry = sorted.get(i);
            topTreatments.put(getTreatmentNameById(entry.getKey()), entry.getValue());
        }
        return topTreatments;
    }

    //Count treatment occurrences
    private Map<Integer, Integer> countTreatmentOccurrences() {
        Map<Integer, Integer> counts = new HashMap<>();
        String sql = "SELECT treatment_id FROM appointments";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String csv = rs.getString("treatment_id");
                if (csv == null || csv.isBlank()) continue;

                for (String idStr : csv.split(",")) {
                    try {
                        int id = Integer.parseInt(idStr.trim());
                        counts.merge(id, 1, Integer::sum);
                    }
                    catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to tally treatment occurrences", e);
        }
        return counts;
    }

    //Get treatment name by id
    private String getTreatmentNameById(int treatmentId) {
        String sql = "SELECT treatment_name FROM treatment_types WHERE treatment_id = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, treatmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("treatment_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch treatment name", e);
        }
        return "N/A";
    }

    //Get previous monthly revenue trend
    @Override
    public Map<String, Double> getMonthlyRevenueTrend(int monthsBack) {

        Map<String, Double> trend = new LinkedHashMap<>();
        DateTimeFormatter labelFormat = DateTimeFormatter.ofPattern("MMM yyyy");

        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM bills " +
                "WHERE MONTH(bill_date) = ? AND YEAR(bill_date) = ?";
        Connection conn = DBConnection.getInstance().getConnection();

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

    //Get daily revenue this month
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
}