package com.dental.clinic.servlet;

import com.dental.clinic.dao.PatientDAO;
import com.dental.clinic.dao.PatientDAOImpl;
import com.dental.clinic.model.Patient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/patients/search")
public class PatientApiServlet extends HttpServlet {

    private final PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("[]");
            return;
        }

        String nicPrefix = request.getParameter("nic");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (nicPrefix == null || nicPrefix.trim().isEmpty()) {
            out.write("[]");
            return;
        }

        List<Patient> matches = patientDAO.findByNicPrefix(nicPrefix.trim());

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < matches.size(); i++) {
            Patient p = matches.get(i);
            if (i > 0) json.append(",");
            json.append("{")
                    .append("\"nic\":\"").append(escape(p.getNic())).append("\",")
                    .append("\"name\":\"").append(escape(p.getName())).append("\",")
                    .append("\"address\":\"").append(escape(p.getAddress())).append("\",")
                    .append("\"contactNumber\":\"").append(escape(p.getContactNumber())).append("\"")
                    .append("}");
        }
        json.append("]");

        out.write(json.toString());
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("loggedInUser") != null;
    }
}