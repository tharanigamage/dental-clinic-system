<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Help - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>
<div class="d-flex">
    <% request.setAttribute("currentPage", "help"); %>
    <jsp:include page="common/sidebar.jsp" />

    <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">

        <h3>Help &amp; Instructions</h3>
        <p class="text-muted">A quick guide for new staff using this system.</p>
        <hr>

        <div class="accordion" id="helpAccordion">

            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#help1">
                        1. Logging In
                    </button>
                </h2>
                <div id="help1" class="accordion-collapse collapse show" data-bs-parent="#helpAccordion">
                    <div class="accordion-body">
                        Enter your username and password on the login page. Only authorized staff accounts
                        (created by an administrator) can access the system. If you enter the wrong
                        credentials, an error message will be shown.
                    </div>
                </div>
            </div>

            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help2">
                        2. Registering a New Appointment
                    </button>
                </h2>
                <div id="help2" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                    <div class="accordion-body">
                        Go to <strong>Appointments</strong> from the sidebar, then click
                        <strong>"+ New Appointment"</strong>. Fill in the patient's name, address, and
                        contact number, then select a dentist, treatment type, date, and time. The system
                        automatically generates a unique appointment number and prevents double-booking
                        the same dentist at the same date and time.
                    </div>
                </div>
            </div>

            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help3">
                        3. Searching and Viewing Appointments
                    </button>
                </h2>
                <div id="help3" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                    <div class="accordion-body">
                        On the <strong>Appointments</strong> page, use the search box to filter by
                        appointment number or patient name. Click <strong>"View"</strong> on any row to
                        see full details in a popup, including patient, dentist, treatment, and status.
                    </div>
                </div>
            </div>

            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help4">
                        4. Updating Appointment Status
                    </button>
                </h2>
                <div id="help4" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                    <div class="accordion-body">
                        Use the status dropdown next to each appointment to mark it as
                        <strong>Pending</strong>, <strong>Completed</strong>, or <strong>Cancelled</strong>.
                        The change is saved automatically.
                    </div>
                </div>
            </div>

            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help5">
                        5. Generating and Printing a Bill
                    </button>
                </h2>
                <div id="help5" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                    <div class="accordion-body">
                        Open an appointment's <strong>View</strong> popup and click
                        <strong>"Generate Bill"</strong>. The system calculates the total from the
                        dentist's consultation fee and treatment cost. Click <strong>"Print Bill"</strong>
                        on the receipt page to print or save it as a PDF. All bills can also be reviewed
                        under the <strong>Billing</strong> section in the sidebar.
                    </div>
                </div>
            </div>

            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#help6">
                        6. Logging Out
                    </button>
                </h2>
                <div id="help6" class="accordion-collapse collapse" data-bs-parent="#helpAccordion">
                    <div class="accordion-body">
                        Click <strong>"Logout"</strong> at the bottom of the sidebar to safely end your
                        session and return to the login page.
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>