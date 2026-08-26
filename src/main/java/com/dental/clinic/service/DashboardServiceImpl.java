package com.dental.clinic.service;

import com.dental.clinic.dao.DashboardDAO;
import com.dental.clinic.dao.DashboardDAOImpl;

public class DashboardServiceImpl implements DashboardService{

    private final DashboardDAO dashboardDAO;

    public DashboardServiceImpl() {
        this.dashboardDAO = new DashboardDAOImpl();
    }

    public DashboardServiceImpl(DashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    @Override
    public int getTodayAppointmentCount() {
        return dashboardDAO.countAppointmentsToday();
    }

    @Override
    public int getThisMonthAppointmentCount() {
        return dashboardDAO.countAppointmentsThisMonth();
    }

    @Override
    public double getThisMonthRevenue() {
        return dashboardDAO.sumRevenueThisMonth();
    }

    @Override
    public int getPendingCount() {
        return dashboardDAO.countByStatus("Pending");
    }

    @Override
    public int getCompletedCount() {
        return dashboardDAO.countByStatus("Completed");
    }

    @Override
    public int getCancelledCount() {
        return dashboardDAO.countByStatus("Cancelled");
    }

    @Override
    public String getMostBookedTreatment() {
        return dashboardDAO.findMostBookedTreatmentName();
    }

}
