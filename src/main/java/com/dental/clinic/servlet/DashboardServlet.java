package com.dental.clinic.servlet;

import com.dental.clinic.service.DashboardService;
import com.dental.clinic.service.DashboardServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

        private final DashboardService dashboardService = new DashboardServiceImpl();

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

            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }

        private boolean isLoggedIn(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            return session != null && session.getAttribute("loggedInUser") != null;
        }
}
