package com.dental.clinic.dao;

import java.util.Map;
import java.util.List;

public interface DashboardDAO {

    int countAppointmentsToday();

    int countAppointmentsThisMonth();

    double sumRevenueThisMonth();

    int countByStatus(String status);

    String findMostBookedTreatmentName();

    Map<String, Double> getMonthlyRevenueTrend(int monthsBack);

    List<Double> getDailyRevenueThisMonth();

    Map<String, Integer> getTopTreatments(int limit);

}
