package com.dental.clinic.servlet;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.TreatmentType;
import com.dental.clinic.service.AppointmentService;
import com.dental.clinic.service.AppointmentServiceImpl;
import com.dental.clinic.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{

        if (!isLoggedIn (request)){
            response.sendRedirect(request.getContextPath()+"/login");
            return;
        }

        List<Appointment>appointments =appointmentService.getAllAppointments();
        List<Dentist>dentists = appointmentService.getAllDentists();
        List<TreatmentType>treatmentTypes = appointmentService.getAllTreatmentTypes();

        request.setAttribute("appointments", appointments);
        request.setAttribute("dentists", dentists);
        request.setAttribute("treatmentTypes", treatmentTypes);

        request.getRequestDispatcher("/appointments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,IOException{

        if (!isLoggedIn(request)){
            response.sendRedirect(request.getContextPath()+ "/login");
            return;
        }
        String action = request.getParameter("action");

        if ("register".equals(action)){
            handleRegister(request,response);
        }
        else if ("updateStatus".equals(action)){
            handleUpdateStatus(request);
            response.sendRedirect(request.getContextPath() + "/appointments");
        }
    }

    private void handleRegister (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {


        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");
        LocalDate date = ValidationUtil.parseDateSafely(request.getParameter("appointmentDate"));
        LocalTime time = ValidationUtil.parseTimeSafely(request.getParameter("appointmentTime"));

            int dentistId = parseIdSafely(request.getParameter("dentistId"));
            int treatmentId = parseIdSafely(request.getParameter("treatmentId"));

            try {
                appointmentService.registerAppointment(name, address, contactNumber, dentistId, treatmentId, date, time);
                response.sendRedirect(request.getContextPath()+ "/appointments");
            }
            catch (IllegalArgumentException e){
                request.setAttribute("errorMessage", e.getMessage());
                request.setAttribute("appointments", appointmentService.getAllAppointments());
                request.setAttribute("dentists", appointmentService.getAllDentists());
                request.setAttribute("treatmentTypes", appointmentService.getAllTreatmentTypes());
                request.getRequestDispatcher("/appointments.jsp").forward(request, response);
            }

        }

        private int parseIdSafely (String value){
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException | NullPointerException e){
            return -1;
        }

    }

    private void handleUpdateStatus (HttpServletRequest request){
        String appointmentNumber = request.getParameter ("appointmentNumber");
        String status = request.getParameter ("status");
        appointmentService.updateAppointmentStatus(appointmentNumber, status);
    }

    private boolean isLoggedIn (HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("loggedInUser") !=null;
    }

}
