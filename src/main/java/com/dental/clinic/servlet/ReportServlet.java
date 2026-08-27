package com.dental.clinic.servlet;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.service.AppointmentService;
import com.dental.clinic.service.AppointmentServiceImpl;
import com.dental.clinic.service.ReportService;
import com.dental.clinic.service.ReportServiceImpl;
import com.dental.clinic.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@WebServlet("/reports")
public class ReportServlet extends HttpServlet{

    private final ReportService reportService = new ReportServiceImpl();
    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String type = request.getParameter("type");
        if (type == null || type.isBlank()) {
            type = "daily";
        }

        request.setAttribute("activeTab", type);

        if ("daily".equals(type)) {
            handleDailyReport(request);
        } else if ("revenue".equals(type)) {
            handleRevenueReport(request);
        } else if ("dentist".equals(type)) {
            handleDentistReport(request);
        }

        request.getRequestDispatcher("/reports.jsp").forward(request, response);
    }

    private void handleDailyReport(HttpServletRequest request) {
        LocalDate selectedDate = ValidationUtil.parseDateSafely(request.getParameter("date"));
        if (selectedDate == null) {
            selectedDate = LocalDate.now();
        }

        List<Appointment> appointments = reportService.getDailyAppointments(selectedDate);

        request.setAttribute("selectedDate", selectedDate);
        request.setAttribute("dailyAppointments", appointments);
    }

    private void handleRevenueReport(HttpServletRequest request) {
        int month = parseIntSafely(request.getParameter("month"), LocalDate.now().getMonthValue());
        int year = parseIntSafely(request.getParameter("year"), LocalDate.now().getYear());

        List<Bill> bills = reportService.getMonthlyRevenue(month, year);

        double totalRevenue = 0;
        for (Bill bill : bills) {
            totalRevenue += bill.getTotalAmount();
        }

        request.setAttribute("selectedMonth", month);
        request.setAttribute("selectedYear", year);
        request.setAttribute("monthlyBills", bills);
        request.setAttribute("totalRevenue", totalRevenue);
    }

    private void handleDentistReport(HttpServletRequest request) {
        List<Dentist> dentists = appointmentService.getAllDentists();
        request.setAttribute("dentists", dentists);

        int dentistId = parseIntSafely(request.getParameter("dentistId"), -1);
        LocalDate fromDate = ValidationUtil.parseDateSafely(request.getParameter("from"));
        LocalDate toDate = ValidationUtil.parseDateSafely(request.getParameter("to"));

        if (fromDate == null) {
            fromDate = LocalDate.now();
        }
        if (toDate == null) {
            toDate = fromDate.plusDays(30);
        }

        request.setAttribute("selectedFrom", fromDate);
        request.setAttribute("selectedTo", toDate);
        request.setAttribute("selectedDentistId", dentistId);

        if (dentistId > 0) {
            List<Appointment> appointments = reportService.getDentistAppointments(dentistId, fromDate, toDate);
            request.setAttribute("dentistAppointments", appointments);
        }
    }

    private int parseIntSafely(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return fallback;
        }
    }

    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("loggedInUser") != null;
    }
}
