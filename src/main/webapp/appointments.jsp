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

        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" style="max-width: 600px;">
            <%= request.getAttribute("errorMessage") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

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
                            <td><%= a.getTreatmentNamesDisplay() %></td>
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
                                        data-treatment-name="<%= a.getTreatmentNamesDisplay() %>"
                                        data-date="<%= a.getAppointmentDate() %>"
                                        data-time="<%= a.getAppointmentTime() %>"
                                        data-status="<%= a.getStatus() %>">
                                    <i class="bi bi-eye"></i>
                                </button>

                                <% if ("Pending".equals(a.getStatus())) { %>
                                <button type="button" class="btn btn-sm btn-outline-secondary"
                                        data-bs-toggle="modal" data-bs-target="#editAppointmentModal"
                                        data-appt-number="<%= a.getAppointmentNumber() %>"
                                        data-date="<%= a.getAppointmentDate() %>"
                                        data-time="<%= a.getAppointmentTime() %>"
                                        data-status="<%= a.getStatus() %>">
                                    <i class="bi bi-pencil-square"></i>
                                </button>
                                <% } %>
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
                    <div class="mb-3 position-relative">
                        <label class="form-label">Patient NIC</label>
                        <input type="text" class="form-control" name="nic" id="nic-input" autocomplete="off" required>
                        <div id="nic-suggestions" class="list-group position-absolute w-100" style="z-index: 1055; display: none;"></div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Patient Name</label>
                        <input type="text" class="form-control" name="name" id="name-input" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Address</label>
                        <input type="text" class="form-control" name="address" id="address-input" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Contact Number</label>
                        <input type="text" class="form-control" name="contactNumber" id="contact-input"
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
                        <label class="form-label">Treatment Type(s)</label>
                        <div class="border rounded p-2" style="max-height: 160px; overflow-y: auto;">
                            <%
                                List<TreatmentType> treatmentTypes = (List<TreatmentType>) request.getAttribute("treatmentTypes");
                                for (TreatmentType t : treatmentTypes) {
                            %>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="treatmentIds"
                                       value="<%= t.getTreatmentId() %>" id="add-treatment-<%= t.getTreatmentId() %>">
                                <label class="form-check-label" for="add-treatment-<%= t.getTreatmentId() %>">
                                    <%= t.getTreatmentName() %> (Rs. <%= t.getCost() %>)
                                </label>
                            </div>
                            <% } %>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6 mb-3">
                            <label class="form-label">Date</label>
                            <input type="date" class="form-control" name="appointmentDate" id="add-date-input" required>
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
                <a id="v-bill-link" href="#" target="_blank" class="btn btn-success btn-sm" style="display:none;">Generate Bill</a>
                <form method="post" action="${pageContext.request.contextPath}/appointments"
                        class="d-inline" id="v-cancel-form" style="display:none;"
                        onsubmit="return confirm('Cancel this appointment? This cannot be undone.');">
                    <input type="hidden" name="action" value="updateStatus">
                    <input type="hidden" name="status" value="Cancelled">
                    <input type="hidden" name="appointmentNumber" id="v-cancel-appt-number">
                    <button type="submit" class="btn btn-danger btn-sm">Cancel Appointment</button>
                </form>
                <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>



<div class="modal fade" id="editAppointmentModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/appointments">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="appointmentNumber" id="edit-appt-number">
                <input type="hidden" name="status" id="edit-status">
                <div class="modal-header">
                    <h5 class="modal-title">Reschedule Appointment</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Date</label>
                        <input type="date" class="form-control" name="appointmentDate" id="edit-date" min="" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Time</label>
                        <input type="time" class="form-control" name="appointmentTime" id="edit-time" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('viewAppointmentModal').addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const status = button.dataset.status;
        const apptNumber = button.dataset.apptNumber;
        const contextPath = '${pageContext.request.contextPath}';

        document.getElementById('v-appt-number').textContent = button.dataset.apptNumber;
        document.getElementById('v-patient-name').textContent = button.dataset.patientName;
        document.getElementById('v-patient-address').textContent = button.dataset.patientAddress;
        document.getElementById('v-patient-contact').textContent = button.dataset.patientContact;
        document.getElementById('v-dentist-name').textContent = button.dataset.dentistName;
        document.getElementById('v-dentist-spec').textContent = button.dataset.dentistSpec;
        document.getElementById('v-treatment-name').textContent = button.dataset.treatmentName;
        document.getElementById('v-date').textContent = button.dataset.date;
        document.getElementById('v-time').textContent = button.dataset.time;
        document.getElementById('v-status').textContent = status;

        const billLink = document.getElementById('v-bill-link');
        const cancelForm = document.getElementById('v-cancel-form');
        const cancelBtn = cancelForm.querySelector('button');

            billLink.classList.remove('disabled');
            billLink.removeAttribute('aria-disabled');
            cancelBtn.disabled = false;
            document.getElementById('v-cancel-appt-number').value = apptNumber;

            if (status === 'Cancelled') {
               billLink.style.display = 'none';
               cancelForm.style.display = 'none';
               cancelBtn.disabled = true;

            } else if (status === 'Completed') {
               billLink.style.display = 'inline-block';
               billLink.removeAttribute('href');
               billLink.classList.add('disabled');
               billLink.setAttribute('aria-disabled', 'true');

               cancelForm.style.display = 'inline-block';
               cancelBtn.disabled = true;

            } else {
               billLink.style.display = 'inline-block';
               billLink.href = contextPath + '/billing?appointmentNumber=' + apptNumber;

               cancelForm.style.display = 'inline-block';
            }
        });

        document.getElementById('editAppointmentModal').addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            document.getElementById('edit-appt-number').value = button.dataset.apptNumber;
            document.getElementById('edit-status').value = button.dataset.status;
            document.getElementById('edit-date').value = button.dataset.date;
            document.getElementById('edit-time').value = button.dataset.time;
        });

        const nicInput = document.getElementById('nic-input');
        const suggestionsBox = document.getElementById('nic-suggestions');
        const nameInput = document.getElementById('name-input');
        const addressInput = document.getElementById('address-input');
        const contactInput = document.getElementById('contact-input');
        const contextPath = '${pageContext.request.contextPath}';
        let searchTimeout;

        nicInput.addEventListener('input', function () {
            clearTimeout(searchTimeout);
            const query = nicInput.value.trim();

            if (query.length < 3) {
                suggestionsBox.style.display = 'none';
                suggestionsBox.innerHTML = '';
                return;
            }

            searchTimeout = setTimeout(function () {
                fetch(contextPath + '/api/patients/search?nic=' + encodeURIComponent(query))
                    .then(response => response.json())
                    .then(patients => {
                        suggestionsBox.innerHTML = '';

                        if (patients.length === 0) {
                            suggestionsBox.style.display = 'none';
                            return;
                        }

                        patients.forEach(function (p) {
                            const item = document.createElement('button');
                            item.type = 'button';
                            item.className = 'list-group-item list-group-item-action';
                            item.textContent = p.nic + ' - ' + p.name;

                            item.addEventListener('click', function () {
                                nicInput.value = p.nic;
                                nameInput.value = p.name;
                                addressInput.value = p.address;
                                contactInput.value = p.contactNumber;
                                suggestionsBox.style.display = 'none';
                            });

                            suggestionsBox.appendChild(item);
                        });

                        suggestionsBox.style.display = 'block';
                    })
                    .catch(() => {
                        suggestionsBox.style.display = 'none';
                    });
            }, 300);
        });

        document.addEventListener('click', function (event) {
            if (event.target !== nicInput) {
                suggestionsBox.style.display = 'none';
            }
        });

        (function () {
                const today = new Date();
                const yyyy = today.getFullYear();
                const mm = String(today.getMonth() + 1).padStart(2, '0');
                const dd = String(today.getDate()).padStart(2, '0');
                const todayStr = yyyy + '-' + mm + '-' + dd;

                document.getElementById('add-date-input').setAttribute('min', todayStr);
                document.getElementById('edit-date').setAttribute('min', todayStr);
            })();

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