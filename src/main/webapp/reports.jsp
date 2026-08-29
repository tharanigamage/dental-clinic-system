<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.Appointment" %>
<%@ page import="com.dental.clinic.model.Bill" %>
<%@ page import="com.dental.clinic.model.Dentist" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reports - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
    <style>
        @media print {
            .no-print { display: none !important; }
        }
    </style>
</head>
<body>
<div class="d-flex">
    <div class="no-print">
        <% request.setAttribute("currentPage", "reports"); %>
        <jsp:include page="common/sidebar.jsp" />
    </div>

    <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">

        <h3>Reports</h3>
        <p class="text-muted">Select a report below and print it when needed.</p>
        <hr>

        <%
            String activeTab = (String) request.getAttribute("activeTab");
            if (activeTab == null) activeTab = "daily";
        %>

        <!-- Tab headers -->
        <ul class="nav nav-tabs no-print mb-3">
            <li class="nav-item">
                <a class="nav-link <%= "daily".equals(activeTab) ? "active" : "" %>"
                   href="${pageContext.request.contextPath}/reports?type=daily">
                    Daily Appointments
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= "revenue".equals(activeTab) ? "active" : "" %>"
                   href="${pageContext.request.contextPath}/reports?type=revenue">
                    Monthly Revenue
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= "dentist".equals(activeTab) ? "active" : "" %>"
                   href="${pageContext.request.contextPath}/reports?type=dentist">
                    Dentist-wise Appointments
                </a>
            </li>
        </ul>


        <!-- DAILY APPOINTMENTS REPORT TAB -->

        <% if ("daily".equals(activeTab)) { %>

        <div class="card">
            <div class="card-body">

                <form method="get" action="${pageContext.request.contextPath}/reports" class="d-flex gap-2 mb-4 no-print">
                    <input type="hidden" name="type" value="daily">
                    <input type="date" class="form-control" name="date" style="max-width: 220px;"
                           value="<%= request.getAttribute("selectedDate") %>">
                    <button type="submit" class="btn btn-primary">View</button>
                    <button type="button" class="btn btn-outline-secondary" onclick="window.print()">Print</button>
                </form>

                <div class="text-center mb-3">
                    <h5>Daily Appointments Report</h5>
                    <p class="text-muted mb-0">Date: <%= request.getAttribute("selectedDate") %></p>
                </div>

                <table class="table table-bordered">
                    <thead class="table-light">
                        <tr>
                            <th>Appt No.</th>
                            <th>Time</th>
                            <th>Patient</th>
                            <th>Dentist</th>
                            <th>Treatment</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Appointment> dailyAppointments = (List<Appointment>) request.getAttribute("dailyAppointments");
                            for (Appointment a : dailyAppointments) {
                        %>
                        <tr>
                            <td><%= a.getAppointmentNumber() %></td>
                            <td><%= a.getAppointmentTime() %></td>
                            <td><%= a.getPatient().getName() %></td>
                            <td><%= a.getDentist().getName() %></td>
                            <td><%= a.getTreatmentNamesDisplay() %></td>
                            <td><%= a.getStatus() %></td>
                        </tr>
                        <% } %>

                        <% if (dailyAppointments.isEmpty()) { %>
                        <tr>
                            <td colspan="6" class="text-center text-muted py-4">No appointments scheduled for this date.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>

                <p class="text-end text-muted small">Total appointments: <%= dailyAppointments.size() %></p>
            </div>
        </div>

        <% } %>

        <!-- MONTHLY REVENUE REPORT TAB -->

        <% if ("revenue".equals(activeTab)) { %>
        <%@ page import="com.dental.clinic.model.Bill" %>

        <div class="card">
            <div class="card-body">

                <form method="get" action="${pageContext.request.contextPath}/reports" class="d-flex gap-2 mb-4 no-print">
                    <input type="hidden" name="type" value="revenue">
                    <select name="month" class="form-select" style="max-width: 160px;">
                        <% String[] monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
                           int selMonth = (Integer) request.getAttribute("selectedMonth");
                           for (int m = 1; m <= 12; m++) { %>
                        <option value="<%= m %>" <%= m == selMonth ? "selected" : "" %>><%= monthNames[m-1] %></option>
                        <% } %>
                    </select>
                    <input type="number" name="year" class="form-control" style="max-width: 120px;"
                           value="<%= request.getAttribute("selectedYear") %>" min="2020" max="2100">
                    <button type="submit" class="btn btn-primary">View</button>
                    <button type="button" class="btn btn-outline-secondary" onclick="window.print()">Print</button>
                </form>

                <%
                    int rMonth = (Integer) request.getAttribute("selectedMonth");
                    String rMonthName = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"}[rMonth - 1];
                %>
                <div class="text-center mb-3">
                    <h5>Monthly Revenue Report</h5>
                    <p class="text-muted mb-0"><%= rMonthName %> <%= request.getAttribute("selectedYear") %></p>
                </div>

                <table class="table table-bordered">
                    <thead class="table-light">
                        <tr>
                            <th>Bill No.</th>
                            <th>Bill Date</th>
                            <th>Patient</th>
                            <th>Treatment</th>
                            <th class="text-end">Consultation Fee (Rs.)</th>
                            <th class="text-end">Treatment Cost (Rs.)</th>
                            <th class="text-end">Total (Rs.)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Bill> monthlyBills = (List<Bill>) request.getAttribute("monthlyBills");
                            for (Bill b : monthlyBills) {
                        %>
                        <tr>
                            <td><%= b.getBillId() %></td>
                            <td><%= b.getBillDate() %></td>
                            <td><%= b.getAppointment().getPatient().getName() %></td>
                            <td><%= b.getAppointment().getTreatmentNamesDisplay() %></td>
                            <td class="text-end"><%= String.format("%.2f", b.getConsultationFee()) %></td>
                            <td class="text-end"><%= String.format("%.2f", b.getTreatmentCost()) %></td>
                            <td class="text-end"><%= String.format("%.2f", b.getTotalAmount()) %></td>
                        </tr>
                        <% } %>

                        <% if (monthlyBills.isEmpty()) { %>
                        <tr>
                            <td colspan="7" class="text-center text-muted py-4">No bills generated in this month.</td>
                        </tr>
                        <% } %>
                    </tbody>
                    <tfoot>
                        <tr class="table-light fw-bold">
                            <td colspan="6" class="text-end">Total Revenue</td>
                            <td class="text-end">Rs. <%= String.format("%.2f", (Double) request.getAttribute("totalRevenue")) %></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
        <% } %>

        <!-- DENTIST-WISE REPORT TAB -->

        <% if ("dentist".equals(activeTab)) { %>

        <div class="card">
            <div class="card-body">

                <form method="get" action="${pageContext.request.contextPath}/reports" class="d-flex gap-2 mb-4 no-print flex-wrap">
                    <input type="hidden" name="type" value="dentist">

                    <select name="dentistId" class="form-select" style="max-width: 260px;">
                        <option value="">Select a dentist</option>
                        <%
                            List<Dentist> dentists = (List<Dentist>) request.getAttribute("dentists");
                            Integer selDentistId = (Integer) request.getAttribute("selectedDentistId");
                            for (Dentist d : dentists) {
                        %>
                        <option value="<%= d.getDentistId() %>" <%= d.getDentistId() == selDentistId ? "selected" : "" %>>
                            <%= d.getName() %> - <%= d.getSpecialization() %>
                        </option>
                        <% } %>
                    </select>

                    <input type="date" class="form-control" name="from" style="max-width: 180px;"
                           value="<%= request.getAttribute("selectedFrom") %>">
                    <input type="date" class="form-control" name="to" style="max-width: 180px;"
                           value="<%= request.getAttribute("selectedTo") %>">

                    <button type="submit" class="btn btn-primary">View</button>
                    <button type="button" class="btn btn-outline-secondary" onclick="window.print()">Print</button>
                </form>

                <%
                    List<Appointment> dentistAppointments = (List<Appointment>) request.getAttribute("dentistAppointments");
                %>

                <% if (dentistAppointments == null) { %>
                    <p class="text-muted text-center py-4">Select a dentist above to view their appointment schedule.</p>
                <% } else { %>

                <div class="text-center mb-3">
                    <h5>Dentist-wise Appointment Report</h5>
                    <p class="text-muted mb-0">
                        <%= request.getAttribute("selectedFrom") %> to <%= request.getAttribute("selectedTo") %>
                    </p>
                </div>

                <table class="table table-bordered">
                    <thead class="table-light">
                        <tr>
                            <th>Appt No.</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Patient</th>
                            <th>Treatment</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Appointment a : dentistAppointments) { %>
                        <tr>
                            <td><%= a.getAppointmentNumber() %></td>
                            <td><%= a.getAppointmentDate() %></td>
                            <td><%= a.getAppointmentTime() %></td>
                            <td><%= a.getPatient().getName() %></td>
                            <td><%= a.getTreatmentNamesDisplay() %></td>
                            <td><%= a.getStatus() %></td>
                        </tr>
                        <% } %>

                        <% if (dentistAppointments.isEmpty()) { %>
                        <tr>
                            <td colspan="6" class="text-center text-muted py-4">No appointments found for this dentist in the selected date range.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>

                <p class="text-end text-muted small">Total appointments: <%= dentistAppointments.size() %></p>

                <% } %>
            </div>
        </div>
        <% } %>

    </div>
</div>
</body>
</html>