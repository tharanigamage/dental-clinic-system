<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
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
                    <div class="card-body">
                        <h6 class="text-muted">This Month's Revenue</h6>
                        <h2 class="text-success">Rs. <%= String.format("%.2f", (Double) request.getAttribute("monthRevenue")) %></h2>
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

        <h5>Appointment Status Breakdown</h5>
        <div class="row g-3">
            <div class="col-md-4">
                <div class="card h-100">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <span>Pending</span>
                        <span class="badge bg-warning text-dark fs-6"><%= request.getAttribute("pendingCount") %></span>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card h-100">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <span>Completed</span>
                        <span class="badge bg-success fs-6"><%= request.getAttribute("completedCount") %></span>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card h-100">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <span>Cancelled</span>
                        <span class="badge bg-danger fs-6"><%= request.getAttribute("cancelledCount") %></span>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>