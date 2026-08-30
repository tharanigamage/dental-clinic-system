<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.Bill" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>
<div class="d-flex">
    <div class="no-print">
        <% request.setAttribute("currentPage", "billing"); %>
        <jsp:include page="common/sidebar.jsp" />
    </div>

    <div class="flex-grow-1 p-4 bg-light d-flex justify-content-center" style="min-height: 100vh;">

        <%
            Bill bill = (Bill) request.getAttribute("bill");
            String errorMessage = (String) request.getAttribute("errorMessage");
        %>

        <!-- Display error message -->
        <% if (errorMessage != null) { %>
        <div style="max-width: 600px; width: 100%;">
            <div class="alert alert-danger" style="width: 100%;"><%= errorMessage %></div>
            <a href="${pageContext.request.contextPath}/appointments" class="btn btn-outline-secondary btn-sm no-print">Back to Appointments</a>
        </div>
        <% } %>

        <% if (bill != null) { %>
        <div class="bill-card" style="max-width: 600px; width: 100%; background: #ffffff;">

            <div class="bill-header">
                <svg class="clinic-icon" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
                    <path d="M100 30c-14 0-22 9-30 9s-18-8-30-4c-13 5-19 20-16 38 2 14 9 24 9 42 0 12 6 25 16 25 9 0 10-16 13-30 2-11 5-18 8-18s6 7 8 18c3 14 4 30 13 30 10 0 16-13 16-25 0-18 7-28 9-42 3-18-3-33-16-38-12-4-22 4-30 4z"
                          fill="#ffffff" fill-opacity="0.95"/>
                </svg>
                <h4>Sunrise Dental Clinic</h4>
                <p>Colombo, Sri Lanka</p>
                <span class="bill-badge">PATIENT RECEIPT</span>
            </div>

            <!-- Patient and appointment details -->
            <div class="bill-body">
                <table class="table info-table mb-3">
                    <tr><th>Bill No.</th><td><%= bill.getBillId() %></td></tr>
                    <tr><th>Bill Date</th><td><%= bill.getBillDate() %></td></tr>
                    <tr><th>Appointment No.</th><td><%= bill.getAppointment().getAppointmentNumber() %></td></tr>
                    <tr><th>Patient Name</th><td><%= bill.getAppointment().getPatient().getName() %></td></tr>
                    <tr><th>Contact Number</th><td><%= bill.getAppointment().getPatient().getContactNumber() %></td></tr>
                    <tr><th>Dentist</th><td><%= bill.getAppointment().getDentist().getName() %></td></tr>
                    <tr><th>Treatment</th><td><%= bill.getAppointment().getTreatmentNamesDisplay() %></td></tr>
                </table>

                <!-- Bill amount -->
                <div class="totals-box">
                    <table class="table mb-0">
                        <tr>
                            <td>Consultation Fee</td>
                            <td class="text-end">Rs. <%= String.format("%.2f", bill.getConsultationFee()) %></td>
                        </tr>
                        <tr>
                            <td>Treatment Cost</td>
                            <td class="text-end">Rs. <%= String.format("%.2f", bill.getTreatmentCost()) %></td>
                        </tr>
                        <tr class="total-row">
                            <td>Total Amount</td>
                            <td class="text-end">Rs. <%= String.format("%.2f", bill.getTotalAmount()) %></td>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="bill-footer">
                Thank you for choosing Sunrise Dental Clinic
            </div>

            <div class="bill-actions text-center no-print">
                <button onclick="window.print()" class="btn btn-primary btn-sm px-4">
                    <i class="bi bi-printer me-1"></i>Print Bill
                </button>
            </div>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>