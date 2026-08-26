package com.dental.clinic.service;

public interface DashboardService {

    int getTodayAppointmentCount();

    int getThisMonthAppointmentCount();

    double getThisMonthRevenue();

    int getPendingCount();

    int getCompletedCount();

    int getCancelledCount();

    String getMostBookedTreatment();
}
