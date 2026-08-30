package com.dental.clinic.servlet;

import com.dental.clinic.service.DashboardService;
import com.dental.clinic.service.DashboardServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.dental.clinic.model.Appointment;
import com.dental.clinic.service.AppointmentService;
import com.dental.clinic.service.AppointmentServiceImpl;

import java.io.IOException;
import  java.util.Map;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

        private final DashboardService dashboardService = new DashboardServiceImpl();
    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    //Load dashboard data
    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            if (!isLoggedIn(request)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            request.setAttribute("todayCount", dashboardService.getTodayAppointmentCount());
            request.setAttribute("monthCount", dashboardService.getThisMonthAppointmentCount());
            request.setAttribute("monthRevenue", dashboardService.getThisMonthRevenue());
            request.setAttribute("pendingCount", dashboardService.getPendingCount());
            request.setAttribute("completedCount", dashboardService.getCompletedCount());
            request.setAttribute("cancelledCount", dashboardService.getCancelledCount());
            request.setAttribute("mostBookedTreatment", dashboardService.getMostBookedTreatment());

            Map<String, Double> revenueTrend = dashboardService.getMonthlyRevenueTrend(6);
            request.setAttribute("revenueTrendLabels", revenueTrend.keySet());
            request.setAttribute("revenueTrendValues", revenueTrend.values());

            List<Double> dailyRevenue = dashboardService.getDailyRevenueThisMonth();
            request.setAttribute("dailyRevenue", dailyRevenue);

            Map<String, Integer> topTreatments = dashboardService.getTopTreatments(5);
            request.setAttribute("topTreatmentLabels", topTreatments.keySet());
            request.setAttribute("topTreatmentValues", topTreatments.values());

            List<Appointment> allAppointments = appointmentService.getAllAppointments();
            List<Appointment> recentAppointments = allAppointments.size() > 5
                    ? allAppointments.subList(0, 5)
                    : allAppointments;
            request.setAttribute("recentAppointments", recentAppointments);

            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }

        private boolean isLoggedIn(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            return session != null && session.getAttribute("loggedInUser") != null;
        }
}
