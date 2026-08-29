package com.dental.clinic.servlet;

import com.dental.clinic.model.User;
import com.dental.clinic.service.StaffService;
import com.dental.clinic.service.StaffServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/myAccount")
public class MyAccountServlet extends HttpServlet {

    private final StaffService staffService = new StaffServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("New password and confirmation do not match.");
            }

            staffService.updateOwnPassword(loggedInUser.getUserId(), currentPassword, newPassword);

            loggedInUser.setPassword(newPassword.trim());
            session.setAttribute("loggedInUser", loggedInUser);


            session.setAttribute("accountFlashSuccess", "Password updated successfully.");

        } catch (IllegalArgumentException e) {
            session.setAttribute("accountFlashError", e.getMessage());
        }

        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath() + "/dashboard");
    }
}