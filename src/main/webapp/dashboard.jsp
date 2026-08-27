<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
</head>
<body>
<div class="d-flex">
    <% request.setAttribute("currentPage", "dashboard"); %>
    <jsp:include page="common/sidebar.jsp" />

    <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">

        <h3>Dashboard</h3>
        <p class="text-muted">A quick overview of clinic activity.</p>
        <hr>

        <div class="row g-3 mb-4">
            <div class="col-md-3">
                <div class="card text-center h-100">
                    <div class="card-body">
                        <h6 class="text-muted">Today's Appointments</h6>
                        <h2 class="text-primary"><%= request.getAttribute("todayCount") %></h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-center h-100">
                    <div class="card-body">
                        <h6 class="text-muted">This Month's Appointments</h6>
                        <h2 class="text-primary"><%= request.getAttribute("monthCount") %></h2>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-center h-100">
                    <div class="card-body text-center">
                        <h6 class="text-muted">This Month's Revenue</h6>
                        <h2 class="text-success">Rs. <%= String.format("%.2f", (Double) request.getAttribute("monthRevenue")) %></h2>
                        <div style="height: 40px; position: relative;">
                            <canvas id="revenueSparkline"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-center h-100">
                    <div class="card-body">
                        <h6 class="text-muted">Most Booked Treatment</h6>
                        <h4 class="text-primary"><%= request.getAttribute("mostBookedTreatment") %></h4>
                    </div>
                </div>
            </div>
        </div>

        <div class="row g-3">
            <div class="col-md-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h6 class="mb-3">Appointment Status</h6>
                        <div class="d-flex align-items-center gap-4">
                            <div style="width: 150px; height: 150px; position: relative; flex-shrink: 0;">
                            <canvas id="statusChart"></canvas>
                            </div>
                            <div class="d-flex flex-column gap-2 ms-4 flex-grow-1">
                                <div class="d-flex justify-content-between align-items-center">
                                    <span class="d-flex align-items-center small">
                                        <span style="width: 10px; height: 10px; border-radius: 50%; background-color: #ffc107; display: inline-block; margin-right: 8px;"></span>
                                        Pending
                                    </span>
                                    <span class="badge rounded-pill" style="background-color: #fff3cd; color: #997404; padding: 6px 12px; font-size: 0.85rem;">
                                        <%= request.getAttribute("pendingCount") %>
                                    </span>
                                </div>
                                <div class="d-flex justify-content-between align-items-center">
                                    <span class="d-flex align-items-center small">
                                        <span style="width: 10px; height: 10px; border-radius: 50%; background-color: #198754; display: inline-block; margin-right: 8px;"></span>
                                        Completed
                                    </span>
                                    <span class="badge rounded-pill" style="background-color: #d1e7dd; color: #0f5132; padding: 6px 12px; font-size: 0.85rem;">
                                        <%= request.getAttribute("completedCount") %>
                                    </span>
                                </div>
                                <div class="d-flex justify-content-between align-items-center">
                                    <span class="d-flex align-items-center small">
                                        <span style="width: 10px; height: 10px; border-radius: 50%; background-color: #dc3545; display: inline-block; margin-right: 8px;"></span>
                                        Cancelled
                                    </span>
                                    <span class="badge rounded-pill" style="background-color: #f8d7da; color: #842029; padding: 6px 12px; font-size: 0.85rem;">
                                        <%= request.getAttribute("cancelledCount") %>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h6 class="mb-3">Revenue Trend (Last 6 Months)</h6>
                            <div style="height: 150px; position: relative;">
                                <canvas id="revenueChart"></canvas>
                            </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h6 class="mb-3">Top Treatments</h6>
                        <div style="height: 150px; position: relative;">
                            <canvas id="treatmentsChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <h5 class="mb-3 mt-4">Recent Appointments</h5>
        <div class="card">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>Appt No.</th>
                            <th>Patient</th>
                            <th>Dentist</th>
                            <th>Treatment</th>
                            <th>Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            java.util.List<com.dental.clinic.model.Appointment> recentAppointments =
                            (java.util.List<com.dental.clinic.model.Appointment>) request.getAttribute("recentAppointments");
                            for (com.dental.clinic.model.Appointment a : recentAppointments) {
                        %>
                        <tr>
                            <td><%= a.getAppointmentNumber() %></td>
                            <td><%= a.getPatient().getName() %></td>
                            <td><%= a.getDentist().getName() %></td>
                            <td><%= a.getTreatmentNamesDisplay() %></td>
                            <td><%= a.getAppointmentDate() %></td>
                            <td>
                            <%
                                String badgeClass = "Completed".equals(a.getStatus()) ? "bg-success"
                                                    : "Cancelled".equals(a.getStatus()) ? "bg-danger" : "bg-warning text-dark";
                            %>
                                <span class="badge <%= badgeClass %>"><%= a.getStatus() %></span>
                            </td>
                        </tr>
                        <% } %>

                        <% if (recentAppointments.isEmpty()) { %>
                        <tr>
                            <td colspan="6" class="text-center text-muted py-4">No appointments yet.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>



<script>
    const statusCtx = document.getElementById('statusChart').getContext('2d');
    new Chart(statusCtx, {
        type: 'doughnut',
        data: {
            labels: ['Pending', 'Completed', 'Cancelled'],
            datasets: [{
                data: [
                    <%= request.getAttribute("pendingCount") %>,
                    <%= request.getAttribute("completedCount") %>,
                    <%= request.getAttribute("cancelledCount") %>
                ],
                backgroundColor: ['#ffc107', '#198754', '#dc3545'],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '65%',
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    enabled: true
                }
            }
        }
    });

        <%
            java.util.Collection<String> labelsCollection = (java.util.Collection<String>) request.getAttribute("revenueTrendLabels");
            java.util.Collection<Double> valuesCollection = (java.util.Collection<Double>) request.getAttribute("revenueTrendValues");

            StringBuilder labelsJs = new StringBuilder();
            for (String label : labelsCollection) {
                if (labelsJs.length() > 0) labelsJs.append(",");
                labelsJs.append("\"").append(label).append("\"");
            }

            StringBuilder valuesJs = new StringBuilder();
            for (Double value : valuesCollection) {
                if (valuesJs.length() > 0) valuesJs.append(",");
                valuesJs.append(value);
            }
        %>
        const revenueLabels = [<%= labelsJs.toString() %>];
        const revenueValues = [<%= valuesJs.toString() %>];

        const revenueCtx = document.getElementById('revenueChart').getContext('2d');
        new Chart(revenueCtx, {
            type: 'line',
            data: {
                labels: revenueLabels,
                datasets: [{
                    label: 'Revenue (Rs.)',
                    data: revenueValues,
                    borderColor: '#0d7377',
                    backgroundColor: 'rgba(13, 115, 119, 0.1)',
                    fill: true,
                    tension: 0.3,
                    pointBackgroundColor: '#0d7377',
                    pointRadius: 4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: { font: { size: 10 } }
                    },
                    x: {
                        ticks: { font: { size: 10 } }
                    }
                }
            }
        });

        <%
                java.util.List<Double> dailyRevenueList = (java.util.List<Double>) request.getAttribute("dailyRevenue");
                StringBuilder dailyRevenueJs = new StringBuilder();
                for (Double v : dailyRevenueList) {
                    if (dailyRevenueJs.length() > 0) dailyRevenueJs.append(",");
                    dailyRevenueJs.append(v);
                }
            %>
            const sparklineValues = [<%= dailyRevenueJs.toString() %>];
            const sparklineLabels = sparklineValues.map((_, i) => i + 1);

            const sparklineCtx = document.getElementById('revenueSparkline').getContext('2d');
            new Chart(sparklineCtx, {
                type: 'line',
                data: {
                    labels: sparklineLabels,
                    datasets: [{
                        data: sparklineValues,
                        borderColor: '#198754',
                        backgroundColor: 'rgba(25, 135, 84, 0.1)',
                        fill: true,
                        tension: 0.4,
                        pointRadius: 0,
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: { display: false },
                        tooltip: { enabled: false }
                    },
                    scales: {
                        x: { display: false },
                        y: { display: false }
                    }
                }
            });


    <%
        java.util.Collection<String> treatmentLabelsCollection = (java.util.Collection<String>) request.getAttribute("topTreatmentLabels");
        java.util.Collection<Integer> treatmentValuesCollection = (java.util.Collection<Integer>) request.getAttribute("topTreatmentValues");

        StringBuilder treatmentLabelsJs = new StringBuilder();
        for (String label : treatmentLabelsCollection) {
            if (treatmentLabelsJs.length() > 0) treatmentLabelsJs.append(",");
            treatmentLabelsJs.append("\"").append(label).append("\"");
        }

        StringBuilder treatmentValuesJs = new StringBuilder();
        for (Integer value : treatmentValuesCollection) {
            if (treatmentValuesJs.length() > 0) treatmentValuesJs.append(",");
            treatmentValuesJs.append(value);
        }
    %>
    const treatmentLabels = [<%= treatmentLabelsJs.toString() %>];
    const treatmentValues = [<%= treatmentValuesJs.toString() %>];

    const treatmentsCtx = document.getElementById('treatmentsChart').getContext('2d');
    new Chart(treatmentsCtx, {
        type: 'bar',
        data: {
            labels: treatmentLabels,
            datasets: [{
                data: treatmentValues,
                backgroundColor: '#0d7377',
                borderRadius: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            indexAxis: 'y',
            plugins: {
                legend: { display: false }
            },
            scales: {
                x: {
                    beginAtZero: true,
                    ticks: { stepSize: 1, font: { size: 10 } }
                },
                y: {
                    ticks: { font: { size: 10 } }
                }
            }
        }
    });
</script>

</body>
</html>