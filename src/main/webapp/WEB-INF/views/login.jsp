<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title> Login - Sunrise Dental Clinic </title>
    <link href="http://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel ="stylesheet">
</head>
<body class="bg-light">
      <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
              <div class="card shadow" style="width: 380px;">
                  <div class="card-body p-4">
                      <h4 class="card-title text-center mb-4">Sunrise Dental Clinic</h4>
                      <p class="text-muted text-center mb-4">Staff Login</p>

                      <% if (request.getAttribute("errorMessage") != null) { %>
                          <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
                      <% } %>

                      <form method="post" action="${pageContext.request.contextPath}/login">
                          <div class="mb-3">
                              <label class="form-label">Username</label>
                              <input type="text" class="form-control" name="username" required>
                          </div>
                          <div class="mb-3">
                              <label class="form-label">Password</label>
                              <input type="password" class="form-control" name="password" required>
                          </div>
                          <button type="submit" class="btn btn-primary w-100">Login</button>
                      </form>
                  </div>
              </div>
      </div>
</body>
</html>