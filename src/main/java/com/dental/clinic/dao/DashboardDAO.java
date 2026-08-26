package com.dental.clinic.dao;

public interface DashboardDAO {

    int countAppointmentsToday();

    int countAppointmentsThisMonth();

    double sumRevenueThisMonth();

    int countByStatus(String status);

    String findMostBookedTreatmentName();

}
