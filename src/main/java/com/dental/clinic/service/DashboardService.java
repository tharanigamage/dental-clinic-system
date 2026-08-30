package com.dental.clinic.service;

import java.util.Map;
import java.util.List;

public interface DashboardService {

    // Today appointment counts
    int getTodayAppointmentCount();

    // This month appointment count
    int getThisMonthAppointmentCount();

    // This month revenue
    double getThisMonthRevenue();

    // Pending count
    int getPendingCount();

    // Completed count
    int getCompletedCount();

    // Cancelled count
    int getCancelledCount();

    // Most booked treatment
    String getMostBookedTreatment();

    // Monthly revenue trend
    Map<String, Double> getMonthlyRevenueTrend(int monthsBack);

    // Daily revenue this month
    List<Double> getDailyRevenueThisMonth();

    // Top treatments count
    Map<String, Integer> getTopTreatments(int limit);

}
