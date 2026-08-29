<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.Bill" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Hide the sidebar and buttons when printing - only the receipt should print */
        @media print {
            .no-print { display: none !important; }

            @page {
                size: A4 portrait;
                margin: 15mm;
            }

            html, body {
                width: 210mm;
                height: auto;
                overflow: visible;
                background: #ffffff !important;
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }

            /* Kill the gray background + min-height that was leaving a gray box */
            .flex-grow-1.p-4 {
                margin-left: 0 !important;
                width: 100% !important;
                height: auto !important;
                min-height: 0 !important;
                overflow: visible !important;
                padding: 0 !important;
                display: block !important;
                background-color: #ffffff !important;
            }

            .card {
                box-shadow: none !important;
                border: none !important;
                max-width: 100% !important;
                width: 100% !important;
                margin: 0 auto !important;
                background-color: #ffffff !important;
            }

            .card-body {
                padding: 0 !important;
                background-color: #ffffff !important;
            }

            /* ---- Info table (Bill No, Date, Patient...) ---- */
            .card table {
                width: 100% !important;
                table-layout: fixed !important;
                border-collapse: collapse !important;
            }

            .card table:first-of-type th {
                width: 35% !important;
                text-align: left !important;
                font-weight: 600 !important;
                padding: 4px 8px 4px 0 !important;
                vertical-align: top !important;
                border: none !important;
            }

            .card table:first-of-type td {
                width: 65% !important;
                text-align: right !important;   /* set to left if you want values on the left instead */
                padding: 4px 0 !important;
                vertical-align: top !important;
                border: none !important;
            }

            .card table:first-of-type tr {
                border-bottom: 1px dotted #ccc !important;
            }

            /* ---- Totals table ---- */
            .card table:last-of-type td:first-child {
                width: 65% !important;
                text-align: left !important;
                padding: 4px 0 !important;
                border: none !important;
            }

            .card table:last-of-type td.text-end {
                width: 35% !important;
                text-align: right !important;
                padding: 4px 0 !important;
                border: none !important;
                white-space: nowrap !important;
            }

            .card table:last-of-type tr.fw-bold td {
                border-top: 1px solid #333 !important;
                padding-top: 6px !important;
            }

            tr {
                break-inside: avoid !important;
                page-break-inside: avoid !important;
            }
        }
    </style>
</head>
<body>
<div class="d-flex">
    <div class="no-print">
        <% request.setAttribute("currentPage", "appointments"); %>
        <jsp:include page="common/sidebar.jsp" />
    </div>

    <div class="flex-grow-1 p-4 bg-light d-flex justify-content-center" style="min-height: 100vh; ">

        <%

        Bill bill = (Bill) request.getAttribute("bill");
                    String errorMessage = (String) request.getAttribute("errorMessage");
        %>

        <% if (errorMessage != null) { %>
        <div class="alert alert-danger" style="max-width: 600px;"><%= errorMessage %></div>
        <a href="${pageContext.request.contextPath}/appointments" class="btn btn-outline-secondary btn-sm no-print">Back to Appointments</a>
        <% } %>

        <% if (bill != null) { %>
        <div class="card" style="max-width: 600px; width: 100%;">
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
                       <tr><th>Treatment</th><td><%= bill.getAppointment().getTreatmentNamesDisplay() %></td></tr>
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
</body>
</html>

