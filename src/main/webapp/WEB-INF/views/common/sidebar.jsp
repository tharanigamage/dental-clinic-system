<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.User" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    String role = (loggedInUser != null) ? loggedInUser.getRole() : "";
    String currentPage = (String) request.getAttribute("currentPage");
    if (currentPage == null) currentPage = "";
%>
<div class="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark" style="width: 240px; min-height: 100vh;">
    <span class="fs-5 fw-bold mb-3">Sunrise Dental Clinic</span>
    <hr>
    <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
            <a href="${pageContext.request.contextPath}/home"
               class="nav-link <%= currentPage.equals("home") ? "active" : "text-white" %>">
                Home
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/appointments"
               class="nav-link <%= currentPage.equals("appointments") ? "active" : "text-white" %>">
                Appointments
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/billing"
               class="nav-link <%= currentPage.equals("billing") ? "active" : "text-white" %>">
                Billing
            </a>
        </li>
        <% if ("Admin".equals(role)) { %>
        <li>
            <a href="${pageContext.request.contextPath}/manageStaff"
               class="nav-link <%= currentPage.equals("staff") ? "active" : "text-white" %>">
                Manage Staff
            </a>
        </li>
        <% } %>
        <li>
            <a href="${pageContext.request.contextPath}/help"
               class="nav-link <%= currentPage.equals("help") ? "active" : "text-white" %>">
                Help
            </a>
        </li>
    </ul>
    <hr>
    <div class="dropdown">
        <span class="d-flex align-items-center text-white text-decoration-none">
            <span><%= loggedInUser != null ? loggedInUser.getUsername() : "" %> <small class="text-muted">(<%= role %>)</small></span>
        </span>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm mt-2 w-100">Logout</a>
    </div>
</div>