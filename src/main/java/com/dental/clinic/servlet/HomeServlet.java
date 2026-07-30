package com.dental.clinic.servlet;

import com.dental.clinic.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        if (loggedInUser == null){
            response.sendRedirect(request.getContextPath()+"/login");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
