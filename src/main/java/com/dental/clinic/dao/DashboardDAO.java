package com.dental.clinic.dao;

import java.util.Map;
import java.util.List;

public interface DashboardDAO {

    // Get today appointments count
    int countAppointmentsToday();

    //Count appointment this month
    int countAppointmentsThisMonth();

    //Total revenue this month
    double sumRevenueThisMonth();

    //Count by status
    int countByStatus(String status);

    //Find most booked treatment
    String findMostBookedTreatmentName();

    // Get monthly revenue data
    Map<String, Double> getMonthlyRevenueTrend(int monthsBack);

    //Daily revenue this month
    List<Double> getDailyRevenueThisMonth();

    //Top treatments count
    Map<String, Integer> getTopTreatments(int limit);

}
