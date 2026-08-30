<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.Patient" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Patients - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>
<div class="d-flex">
    <% request.setAttribute("currentPage", "patients"); %>
    <jsp:include page="common/sidebar.jsp" />

    <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">

        <h3>Patients</h3>
        <p class="text-muted">All patients registered in the system.</p>
        <hr>

        <input type="text" id="searchBox" class="form-control mb-3" style="max-width: 320px;"
               placeholder="Search by NIC or patient name..." onkeyup="filterTable()">

        <div class="card">
            <div class="table-responsive">
                <table class="table table-hover mb-0" id="patientsTable">
                    <thead class="table-light">
                        <tr>
                            <th>NIC</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Contact Number</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Patient> patients = (List<Patient>) request.getAttribute("patients");
                            for (Patient p : patients) {
                        %>
                        <tr>
                            <td><%= p.getNic() %></td>
                            <td><%= p.getName() %></td>
                            <td><%= p.getAddress() %></td>
                            <td><%= p.getContactNumber() %></td>
                        </tr>
                        <% } %>

                        <% if (patients.isEmpty()) { %>
                        <tr>
                            <td colspan="5" class="text-center text-muted py-4">No patients registered yet.</td>
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
        const rows = document.querySelectorAll('#patientsTable tbody tr');
        rows.forEach(function (row) {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(query) ? '' : 'none';
        });
    }
</script>
</body>
</html>