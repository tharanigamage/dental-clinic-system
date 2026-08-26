<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>
<div class="login-split">

    <!-- Illustration panel -->
    <div class="login-illustration-panel">
        <svg viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg" fill="none">
            <path d="M100 30c-14 0-22 9-30 9s-18-8-30-4c-13 5-19 20-16 38 2 14 9 24 9 42 0 12 6 25 16 25 9 0 10-16 13-30 2-11 5-18 8-18s6 7 8 18c3 14 4 30 13 30 10 0 16-13 16-25 0-18 7-28 9-42 3-18-3-33-16-38-12-4-22 4-30 4z"
                  fill="#ffffff" fill-opacity="0.95"/>
        </svg>
        <h2>Sunrise Dental Clinic</h2>
        <p>Manage appointments, patients, and billing all in one place built for the clinic's front-desk team.</p>
    </div>

    <!-- RIGHT — Login form panel -->
    <div class="login-form-panel">
        <div class="login-form-inner">
            <h4 class="fw-bold mb-1">Welcome back</h4>
            <p class="text-muted mb-4">Sign in to continue to your dashboard</p>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
            <% } %>

            <form method="post" action="${pageContext.request.contextPath}/login">
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" class="form-control" name="username" required autofocus>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" class="form-control" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100 py-2 mt-2">Login</button>
            </form>
        </div>
    </div>

</div>
</body>
</html>