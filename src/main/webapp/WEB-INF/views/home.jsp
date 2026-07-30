<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="d-flex">
        <% request.setAttribute("currentPage", "home"); %>
        <jsp:include page="common/sidebar.jsp" />

        <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">
            <%
                User loggedInUser = (User) session.getAttribute("loggedInUser");
            %>
            <h3>Welcome, <%= loggedInUser.getUsername() %>!</h3>
            <p class="text-muted">Role: <%= loggedInUser.getRole() %></p>
            <hr>

            <div class="row g-3 mt-2">
                <div class="col-md-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Register Appointment</h5>
                            <p class="card-text text-muted">Add a new patient appointment.</p>
                            <a href="${pageContext.request.contextPath}/registerAppointment" class="btn btn-primary btn-sm">Open</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Search Appointment</h5>
                            <p class="card-text text-muted">Find appointment by number.</p>
                            <a href="${pageContext.request.contextPath}/searchAppointment" class="btn btn-primary btn-sm">Open</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Billing</h5>
                            <p class="card-text text-muted">Generate and print a bill.</p>
                            <a href="${pageContext.request.contextPath}/billing" class="btn btn-primary btn-sm">Open</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>