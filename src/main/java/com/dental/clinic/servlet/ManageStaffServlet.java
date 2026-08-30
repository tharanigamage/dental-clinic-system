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
import java.util.List;

@WebServlet("/manageStaff")
public class ManageStaffServlet extends HttpServlet{

    private final StaffService staffService = new StaffServiceImpl();

    // Load staff management page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        List<User> staffList = staffService.getAllStaff();
        request.setAttribute("staffList", staffList);

        request.getRequestDispatcher("/manageStaff.jsp").forward(request, response);
    }

    //Handle staff management
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request, response);
        } else if ("edit".equals(action)){
            handleEdit(request,response);
        } else if ("delete".equals(action)) {
            handleDelete(request);
            request.getSession().setAttribute("successMessage", "Staff account removed successfully.");
            response.sendRedirect(request.getContextPath() + "/manageStaff");
        }
    }

    // Handle add new staff account
    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            staffService.addStaff(username, password, role);
            request.getSession().setAttribute("successMessage", "Staff account added successfully.");
            response.sendRedirect(request.getContextPath() + "/manageStaff");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("staffList", staffService.getAllStaff());
            request.getRequestDispatcher("/manageStaff.jsp").forward(request, response);
        }
    }

    // Handle edit staff account
    private void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            staffService.updateStaff(userId, username, password, role);
            request.getSession().setAttribute("successMessage", "Staff account updated successfully.");
            response.sendRedirect(request.getContextPath() + "/manageStaff");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("staffList", staffService.getAllStaff());
            request.getRequestDispatcher("/manageStaff.jsp").forward(request, response);
        }
    }

    // Handle delete staff account
    private void handleDelete(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        staffService.deleteStaff(userId);
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        return loggedInUser != null && "Admin".equals(loggedInUser.getRole());
    }

}
