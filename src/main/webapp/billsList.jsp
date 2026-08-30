<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.Bill" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bills - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>
<div class="d-flex">
    <% request.setAttribute("currentPage", "billing"); %>
    <jsp:include page="common/sidebar.jsp" />

    <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">

        <h3>Bills</h3>
        <p class="text-muted">All generated bills across appointments.</p>
        <hr>

        <input type="text" id="searchBox" class="form-control mb-3" style="max-width: 320px;"
               placeholder="Search by bill no. or patient name..." onkeyup="filterTable()">

        <!-- Bills grid -->
        <div class="card">
            <div class="table-responsive">
                <table class="table table-hover mb-0" id="billsTable">
                    <thead class="table-light">
                        <tr>
                            <th>Bill No.</th>
                            <th>Appt No.</th>
                            <th>Patient</th>
                            <th>Dentist</th>
                            <th>Treatment</th>
                            <th>Bill Date</th>
                            <th class="text-end">Total (Rs.)</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Bill> bills = (List<Bill>) request.getAttribute("bills");
                            for (Bill b : bills) {
                        %>
                        <tr>
                            <td><%= b.getBillId() %></td>
                            <td><%= b.getAppointment().getAppointmentNumber() %></td>
                            <td><%= b.getAppointment().getPatient().getName() %></td>
                            <td><%= b.getAppointment().getDentist().getName() %></td>
                            <td><%= b.getAppointment().getTreatmentNamesDisplay() %></td>
                            <td><%= b.getBillDate() %></td>
                            <td class="text-end"><%= String.format("%.2f", b.getTotalAmount()) %></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/billing?appointmentNumber=<%= b.getAppointment().getAppointmentNumber() %>"
                                   class="btn btn-sm btn-outline-primary">View / Print</a>
                            </td>
                        </tr>
                        <% } %>

                        <% if (bills.isEmpty()) { %>
                        <tr>
                            <td colspan="8" class="text-center text-muted py-4">No bills generated yet.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    // Filter table
    function filterTable() {
        const query = document.getElementById('searchBox').value.toLowerCase();
        const rows = document.querySelectorAll('#billsTable tbody tr');
        rows.forEach(function (row) {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(query) ? '' : 'none';
        });
    }
</script>
</body>
</html>