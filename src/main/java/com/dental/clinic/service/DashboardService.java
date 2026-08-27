package com.dental.clinic.service;

import java.util.Map;
import java.util.List;

public interface DashboardService {

    int getTodayAppointmentCount();

    int getThisMonthAppointmentCount();

    double getThisMonthRevenue();

    int getPendingCount();

    int getCompletedCount();

    int getCancelledCount();

    String getMostBookedTreatment();

    Map<String, Double> getMonthlyRevenueTrend(int monthsBack);

    List<Double> getDailyRevenueThisMonth();

    Map<String, Integer> getTopTreatments(int limit);

}
