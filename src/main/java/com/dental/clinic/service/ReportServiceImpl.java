package com.dental.clinic.service;

import com.dental.clinic.dao.ReportDAO;
import com.dental.clinic.dao.ReportDAOImpl;
import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;

import java.time.LocalDate;
import java.util.List;

public class ReportServiceImpl implements ReportService{

    private final ReportDAO reportDAO;

    public ReportServiceImpl() {
        this.reportDAO = new ReportDAOImpl();
    }

    public ReportServiceImpl(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    public List<Appointment> getDailyAppointments(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return reportDAO.findAppointmentsByDate(date);
    }

    @Override
    public List<Bill> getMonthlyRevenue(int month, int year) {
        return reportDAO.findBillsByMonth(month, year);
    }

    @Override
    public List<Appointment> getDentistAppointments(int dentistId, LocalDate from, LocalDate to) {
        if (from == null) {
            from = LocalDate.now();
        }
        if (to == null) {
            to = from.plusDays(30);
        }
        return reportDAO.findAppointmentsByDentistAndDateRange(dentistId, from, to);
    }

}
