<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.User" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    String role = (loggedInUser != null) ? loggedInUser.getRole() : "";
    String currentPage = (String) request.getAttribute("currentPage");
    if (currentPage == null) currentPage = "";

    String accountFlashSuccess = (String) session.getAttribute("accountFlashSuccess");
    String accountFlashError = (String) session.getAttribute("accountFlashError");
    session.removeAttribute("accountFlashSuccess");
    session.removeAttribute("accountFlashError");
%>
<div class="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark" style="width: 240px; min-height: 100vh;">
    <span class="fs-5 fw-bold mb-3">Sunrise Dental Clinic</span>
    <hr>
    <ul class="nav nav-pills flex-column mb-auto">
        <li>
            <a href="${pageContext.request.contextPath}/dashboard"
                class="nav-link <%= currentPage.equals("dashboard") ? "active" : "text-white" %>">
                 <i class="bi bi-house me-2"></i>Dashboard
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/appointments"
               class="nav-link <%= currentPage.equals("appointments") ? "active" : "text-white" %>">
                <i class="bi bi-calendar-check me-2"></i>Appointments
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/billing"
               class="nav-link <%= currentPage.equals("billing") ? "active" : "text-white" %>">
                <i class="bi bi-receipt me-2"></i>Billing
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/reports"
               class="nav-link <%= currentPage.equals("reports") ? "active" : "text-white" %>">
                <i class="bi bi-bar-chart-line me-2"></i>Reports
            </a>
        </li>
        <% if ("Admin".equals(role)) { %>
        <li>
            <a href="${pageContext.request.contextPath}/manageStaff"
               class="nav-link <%= currentPage.equals("staff") ? "active" : "text-white" %>">
                <i class="bi bi-people me-2"></i>Manage Staff
            </a>
        </li>
        <% } %>
        <li>
            <a href="${pageContext.request.contextPath}/help"
               class="nav-link <%= currentPage.equals("help") ? "active" : "text-white" %>">
                <i class="bi bi-question-circle me-2"></i>Help
            </a>
        </li>
    </ul>
    <hr>
    <div class="dropdown">
        <button type="button" class="btn btn-outline-light btn-sm mt-2 w-100" data-bs-toggle="modal" data-bs-target="#myAccountModal">
            <i class="bi bi-person-gear me-1"></i>My Account
        </button>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger btn-sm mt-2 w-100">
            <i class="bi bi-box-arrow-right me-1"></i>Logout
        </a>
    </div>
</div>

<div class="modal fade" id="myAccountModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">My Account</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <table class="table table-sm mb-4">
                    <tr><th>Username</th><td><%= loggedInUser != null ? loggedInUser.getUsername() : "" %></td></tr>
                    <tr><th>Role</th><td><%= role %></td></tr>
                </table>

                <% if (accountFlashSuccess != null) { %>
                <div class="alert alert-success"><%= accountFlashSuccess %></div>
                <% } %>
                <% if (accountFlashError != null) { %>
                <div class="alert alert-danger"><%= accountFlashError %></div>
                <% } %>

                <form method="post" action="${pageContext.request.contextPath}/myAccount">
                    <div class="mb-3">
                        <label class="form-label">Current Password</label>
                        <input type="password" class="form-control" name="currentPassword" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">New Password</label>
                        <input type="password" class="form-control" name="newPassword" minlength="4" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Confirm New Password</label>
                        <input type="password" class="form-control" name="confirmPassword" minlength="4" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Update Password</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<% if (accountFlashSuccess != null || accountFlashError != null) { %>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        new bootstrap.Modal(document.getElementById('myAccountModal')).show();
    });
</script>
<% } %>