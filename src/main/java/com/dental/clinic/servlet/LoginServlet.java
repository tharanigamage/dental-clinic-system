package com.dental.clinic.servlet;

import com.dental.clinic.model.User;
import com.dental.clinic.service.AuthenticationService;
import com.dental.clinic.service.AuthenticationServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthenticationService authService = new AuthenticationServiceImpl();

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request,response);
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = authService.login(username,password);

        if (user != null){
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
            response.sendRedirect(request.getContextPath()+"/home");
        }
        else {
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request,response);
        }
    }

}
