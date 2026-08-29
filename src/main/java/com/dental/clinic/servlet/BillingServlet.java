package com.dental.clinic.servlet;

import com.dental.clinic.model.Bill;
import com.dental.clinic.service.BillingService;
import com.dental.clinic.service.BillingServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet{

    private final BillingService billingService = new BillingServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String appointmentNumber = request.getParameter("appointmentNumber");

        if (appointmentNumber == null || appointmentNumber.isBlank()) {
            List<Bill> bills = billingService.getAllBills();
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("/WEB-INF/views/billsList.jsp").forward(request, response);
            return;
        }

        try {
            Bill bill = billingService.generateBill(appointmentNumber.trim());
            request.setAttribute("bill", bill);
        }
        catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/bill.jsp").forward(request, response);
    }

    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("loggedInUser") != null;
    }

}
