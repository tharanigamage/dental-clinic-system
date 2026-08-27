package com.dental.clinic.servlet;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.service.AppointmentService;
import com.dental.clinic.service.AppointmentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/appointments/*")
public class AppointmentApiServlet extends HttpServlet{

    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Not authenticated\"}");
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Appointment number required in URL, e.g. /api/appointments/APT001\"}");
            return;
        }

        String appointmentNumber = pathInfo.substring(1);   // remove the leading "/"
        Appointment appointment = appointmentService.searchAppointment(appointmentNumber);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (appointment == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("{\"error\":\"No appointment found with number " + appointmentNumber + "\"}");
            return;
        }

        String json = "{"
                + "\"appointmentNumber\":\"" + escape(appointment.getAppointmentNumber()) + "\","
                + "\"patientName\":\"" + escape(appointment.getPatient().getName()) + "\","
                + "\"contactNumber\":\"" + escape(appointment.getPatient().getContactNumber()) + "\","
                + "\"dentistName\":\"" + escape(appointment.getDentist().getName()) + "\","
                + "\"treatmentName\":\"" + escape(appointment.getTreatmentType().getTreatmentName()) + "\","
                + "\"appointmentDate\":\"" + appointment.getAppointmentDate() + "\","
                + "\"appointmentTime\":\"" + appointment.getAppointmentTime() + "\","
                + "\"status\":\"" + escape(appointment.getStatus()) + "\""
                + "}";

        out.write(json);
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
