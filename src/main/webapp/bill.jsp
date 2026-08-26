<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.Bill" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill - Sunrise Dental Clinic</title>
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
        <% request.setAttribute("currentPage", "appointments"); %>
        <jsp:include page="common/sidebar.jsp" />
    </div>

    <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">

        <%

        Bill bill = (Bill) request.getAttribute("bill");
                    String errorMessage = (String) request.getAttribute("errorMessage");
        %>

        <% if (errorMessage != null) { %>
        <div class="alert alert-danger" style="max-width: 600px;"><%= errorMessage %></div>
        <a href="${pageContext.request.contextPath}/appointments" class="btn btn-outline-secondary btn-sm no-print">Back to Appointments</a>
        <% } %>

        <% if (bill != null) { %>
        <div class="card mx-auto" style="max-width: 600px;">
            <div class="card-body">

                 <div class="text-center mb-4">
                      <h4>Sunrise Dental Clinic</h4>
                      <p class="text-muted mb-0">Colombo, Sri Lanka</p>
                      <hr>
                      <h5>Patient Bill / Receipt</h5>
                 </div>

                 <table class="table table-sm">
                       <tr><th>Bill No.</th><td><%= bill.getBillId() %></td></tr>
                       <tr><th>Bill Date</th><td><%= bill.getBillDate() %></td></tr>
                       <tr><th>Appointment No.</th><td><%= bill.getAppointment().getAppointmentNumber() %></td></tr>
                       <tr><th>Patient Name</th><td><%= bill.getAppointment().getPatient().getName() %></td></tr>
                       <tr><th>Contact Number</th><td><%= bill.getAppointment().getPatient().getContactNumber() %></td></tr>
                       <tr><th>Dentist</th><td><%= bill.getAppointment().getDentist().getName() %></td></tr>
                       <tr><th>Treatment</th><td><%= bill.getAppointment().getTreatmentType().getTreatmentName() %></td></tr>
                 </table>

                 <hr>

                 <table class="table table-sm">
                       <tr>
                             <td>Consultation Fee</td>
                             <td class="text-end">Rs. <%= String.format("%.2f", bill.getConsultationFee()) %></td>
                       </tr>
                       <tr>
                              <td>Treatment Cost</td>
                              <td class="text-end">Rs. <%= String.format("%.2f", bill.getTreatmentCost()) %></td>
                       </tr>
                       <tr class="fw-bold">
                             <td>Total Amount</td>
                             <td class="text-end">Rs. <%= String.format("%.2f", bill.getTotalAmount()) %></td>
                       </tr>
                 </table>

                 <div class="text-center text-muted mt-4">
                       <small>Thank you for choosing Sunrise Dental Clinic</small>
                 </div>

                 <div class="text-center mt-4 no-print">
                      <button onclick="window.print()" class="btn btn-primary btn-sm">Print Bill</button>
                      <a href="${pageContext.request.contextPath}/appointments" class="btn btn-outline-secondary btn-sm">Back to Appointments</a>
                 </div>
            </div>
         </div>
        <% } %>
    </div>
</div>
</body>
</html>

