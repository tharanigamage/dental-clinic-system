package com.dental.clinic.service;

import com.dental.clinic.dao.DashboardDAO;
import com.dental.clinic.dao.DashboardDAOImpl;

import java.util.Map;
import java.util.List;

public class DashboardServiceImpl implements DashboardService{

    private final DashboardDAO dashboardDAO;

    // Constructor
    public DashboardServiceImpl() {
        this.dashboardDAO = new DashboardDAOImpl();
    }

    public DashboardServiceImpl(DashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    // Get today appointment count
    @Override
    public int getTodayAppointmentCount() {
        return dashboardDAO.countAppointmentsToday();
    }

    // Get this month appointment count
    @Override
    public int getThisMonthAppointmentCount() {
        return dashboardDAO.countAppointmentsThisMonth();
    }

    // Get this month revenue
    @Override
    public double getThisMonthRevenue() {
        return dashboardDAO.sumRevenueThisMonth();
    }

    // Get pending count
    @Override
    public int getPendingCount() {
        return dashboardDAO.countByStatus("Pending");
    }

    // Get completed count
    @Override
    public int getCompletedCount() {
        return dashboardDAO.countByStatus("Completed");
    }

    // Get cancelled count
    @Override
    public int getCancelledCount() {
        return dashboardDAO.countByStatus("Cancelled");
    }

    // Get most booked treatment
    @Override
    public String getMostBookedTreatment() {
        return dashboardDAO.findMostBookedTreatmentName();
    }

    // Get monthly revenue trend
    @Override
    public Map<String, Double> getMonthlyRevenueTrend(int monthsBack) {
        return dashboardDAO.getMonthlyRevenueTrend(monthsBack);
    }

    // Get daily revenue this month
    @Override
    public List<Double> getDailyRevenueThisMonth() {
        return dashboardDAO.getDailyRevenueThisMonth();
    }

    // Get top treatments
    @Override
    public Map<String, Integer> getTopTreatments(int limit) {
        return dashboardDAO.getTopTreatments(limit);
    }

}
