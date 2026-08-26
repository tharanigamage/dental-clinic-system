package com.dental.clinic.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException{

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length());

        boolean isPublic = path.equals("/login")
                || path.equals("/")
                || path.equals("index.jsp")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/");

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("loggedInUser") != null;

        if (isPublic || isLoggedIn) {
            chain.doFilter(servletRequest, servletResponse);
        }else {
            response.sendRedirect(request.getContextPath() + "/login");
        }

    }
}
