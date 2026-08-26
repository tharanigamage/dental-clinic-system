<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.Appointment" %>
<%@ page import="com.dental.clinic.model.Dentist" %>
<%@ page import="com.dental.clinic.model.TreatmentType" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Appointments - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>
<div class="d-flex">
    <% request.setAttribute("currentPage", "appointments"); %>
    <jsp:include page="common/sidebar.jsp" />

    <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">

        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3>Appointments</h3>
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addAppointmentModal">
                + New Appointment
            </button>
        </div>

        <input type="text" id="searchBox" class="form-control mb-3" style="max-width: 320px;"
               placeholder="Search by appointment number or patient name..." onkeyup="filterTable()">

        <div class="card">
            <div class="table-responsive">
                <table class="table table-hover mb-0" id="appointmentsTable">
                    <thead class="table-light">
                        <tr>
                            <th>App.No</th>
                            <th>Patient</th>
                            <th>Dentist</th>
                            <th>Treatment</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
                            for (Appointment a : appointments) {
                        %>
                        <tr>
                            <td><%= a.getAppointmentNumber() %></td>
                            <td><%= a.getPatient().getName() %></td>
                            <td><%= a.getDentist().getName() %></td>
                            <td><%= a.getTreatmentType().getTreatmentName() %></td>
                            <td><%= a.getAppointmentDate() %></td>
                            <td><%= a.getAppointmentTime() %></td>
                            <td>
                                <%
                                    String badgeClass = "Completed".equals(a.getStatus()) ? "bg-success"
                                            : "Cancelled".equals(a.getStatus()) ? "bg-danger" : "bg-warning text-dark";
                                %>
                                <span class="badge <%= badgeClass %>"><%= a.getStatus() %></span>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-primary"
                                        data-bs-toggle="modal" data-bs-target="#viewAppointmentModal"
                                        data-appt-number="<%= a.getAppointmentNumber() %>"
                                        data-patient-name="<%= a.getPatient().getName() %>"
                                        data-patient-address="<%= a.getPatient().getAddress() %>"
                                        data-patient-contact="<%= a.getPatient().getContactNumber() %>"
                                        data-dentist-name="<%= a.getDentist().getName() %>"
                                        data-dentist-spec="<%= a.getDentist().getSpecialization() %>"
                                        data-treatment-name="<%= a.getTreatmentType().getTreatmentName() %>"
                                        data-date="<%= a.getAppointmentDate() %>"
                                        data-time="<%= a.getAppointmentTime() %>"
                                        data-status="<%= a.getStatus() %>">
                                    View
                                </button>

                                <form method="post" action="${pageContext.request.contextPath}/appointments"
                                      class="d-inline">
                                    <input type="hidden" name="action" value="updateStatus">
                                    <input type="hidden" name="appointmentNumber" value="<%= a.getAppointmentNumber() %>">
                                    <select name="status" class="form-select form-select-sm d-inline w-auto"
                                            onchange="this.form.submit()">
                                        <option value="Pending" <%= "Pending".equals(a.getStatus()) ? "selected" : "" %>>Pending</option>
                                        <option value="Completed" <%= "Completed".equals(a.getStatus()) ? "selected" : "" %>>Completed</option>
                                        <option value="Cancelled" <%= "Cancelled".equals(a.getStatus()) ? "selected" : "" %>>Cancelled</option>
                                    </select>
                                </form>
                            </td>
                        </tr>
                        <% } %>

                        <% if (appointments.isEmpty()) { %>
                        <tr>
                            <td colspan="8" class="text-center text-muted py-4">No appointments yet. Click "+ New Appointment" to add one.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addAppointmentModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/appointments">
                <input type="hidden" name="action" value="register">
                <div class="modal-header">
                    <h5 class="modal-title">Schedule New Appointment</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Patient Name</label>
                        <input type="text" class="form-control" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Address</label>
                        <input type="text" class="form-control" name="address" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Contact Number</label>
                        <input type="text" class="form-control" name="contactNumber"
                               pattern="[0-9]{10}" title="Enter a 10-digit phone number" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Dentist</label>
                        <select class="form-select" name="dentistId" required>
                            <option value="" disabled selected>Select a dentist</option>
                            <%
                                List<Dentist> dentists = (List<Dentist>) request.getAttribute("dentists");
                                for (Dentist d : dentists) {
                            %>
                            <option value="<%= d.getDentistId() %>">
                                <%= d.getName() %> - <%= d.getSpecialization() %> (Rs. <%= d.getConsultationFee() %>)
                            </option>
                            <% } %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Treatment Type</label>
                        <select class="form-select" name="treatmentId" required>
                            <option value="" disabled selected>Select a treatment</option>
                            <%
                                List<TreatmentType> treatmentTypes = (List<TreatmentType>) request.getAttribute("treatmentTypes");
                                for (TreatmentType t : treatmentTypes) {
                            %>
                            <option value="<%= t.getTreatmentId() %>">
                                <%= t.getTreatmentName() %> (Rs. <%= t.getCost() %>)
                            </option>
                            <% } %>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col-6 mb-3">
                            <label class="form-label">Date</label>
                            <input type="date" class="form-control" name="appointmentDate" required>
                        </div>
                        <div class="col-6 mb-3">
                            <label class="form-label">Time</label>
                            <input type="time" class="form-control" name="appointmentTime" required>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Schedule Appointment</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="viewAppointmentModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Appointment Details</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <table class="table table-sm">
                    <tr><th>Appointment Number</th><td id="v-appt-number"></td></tr>
                    <tr><th>Patient Name</th><td id="v-patient-name"></td></tr>
                    <tr><th>Address</th><td id="v-patient-address"></td></tr>
                    <tr><th>Contact Number</th><td id="v-patient-contact"></td></tr>
                    <tr><th>Dentist</th><td id="v-dentist-name"></td></tr>
                    <tr><th>Specialization</th><td id="v-dentist-spec"></td></tr>
                    <tr><th>Treatment</th><td id="v-treatment-name"></td></tr>
                    <tr><th>Date</th><td id="v-date"></td></tr>
                    <tr><th>Time</th><td id="v-time"></td></tr>
                    <tr><th>Status</th><td id="v-status"></td></tr>
                </table>
            </div>
            <div class="modal-footer">
                <a id="v-bill-link" href="#" class="btn btn-success btn-sm">Generate Bill</a>
                <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('viewAppointmentModal').addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;

        document.getElementById('v-appt-number').textContent = button.dataset.apptNumber;
        document.getElementById('v-patient-name').textContent = button.dataset.patientName;
        document.getElementById('v-patient-address').textContent = button.dataset.patientAddress;
        document.getElementById('v-patient-contact').textContent = button.dataset.patientContact;
        document.getElementById('v-dentist-name').textContent = button.dataset.dentistName;
        document.getElementById('v-dentist-spec').textContent = button.dataset.dentistSpec;
        document.getElementById('v-treatment-name').textContent = button.dataset.treatmentName;
        document.getElementById('v-date').textContent = button.dataset.date;
        document.getElementById('v-time').textContent = button.dataset.time;
        document.getElementById('v-status').textContent = button.dataset.status;

        const contextPath = '${pageContext.request.contextPath}';
        document.getElementById('v-bill-link').href = contextPath + '/billing?appointmentNumber=' + button.dataset.apptNumber;
    });

    function filterTable() {
        const query = document.getElementById('searchBox').value.toLowerCase();
        const rows = document.querySelectorAll('#appointmentsTable tbody tr');
        rows.forEach(function (row) {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(query) ? '' : 'none';
        });
    }
</script>
</body>
</html>