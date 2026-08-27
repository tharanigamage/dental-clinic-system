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
        <svg viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
            <defs>
                <linearGradient id="toothShine" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#ffffff" stop-opacity="1"/>
                    <stop offset="100%" stop-color="#eafaf9" stop-opacity="1"/>
                </linearGradient>
            </defs>
            <ellipse cx="100" cy="178" rx="42" ry="8" fill="#000000" opacity="0.08"/>
            <path d="M100 18
                     C 78 18, 68 28, 56 30
                     C 40 33, 26 28, 18 40
                     C 8 55, 12 74, 20 90
                     C 28 106, 32 118, 34 138
                     C 36 156, 42 172, 56 172
                     C 68 172, 72 152, 76 128
                     C 78 112, 84 100, 100 100
                     C 116 100, 122 112, 124 128
                     C 128 152, 132 172, 144 172
                     C 158 172, 164 156, 166 138
                     C 168 118, 172 106, 180 90
                     C 188 74, 192 55, 182 40
                     C 174 28, 160 33, 144 30
                     C 132 28, 122 18, 100 18 Z"
                  fill="url(#toothShine)" stroke="#d5efee" stroke-width="1.5"/>
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