<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.dental.clinic.model.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Staff - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>
<div class="d-flex">
    <% request.setAttribute("currentPage", "staff"); %>
    <jsp:include page="common/sidebar.jsp" />

    <div class="flex-grow-1 p-4 bg-light" style="min-height: 100vh;">

        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3>Manage Staff</h3>
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addStaffModal">
                + Add Staff Account
            </button>
        </div>
        <p class="text-muted">Admin only — create, edit, and remove staff login accounts.</p>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger" style="max-width: 600px;"><%= request.getAttribute("errorMessage") %></div>
        <% } %>

        <div class="card">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>User ID</th>
                            <th>Username</th>
                            <th>Role</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<User> staffList = (List<User>) request.getAttribute("staffList");
                            User loggedInUser = (User) session.getAttribute("loggedInUser");
                            for (User u : staffList) {
                        %>
                        <tr>
                            <td><%= u.getUserId() %></td>
                            <td><%= u.getUsername() %></td>
                            <td><span class="badge bg-secondary"><%= u.getRole() %></span></td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-primary"
                                        data-bs-toggle="modal" data-bs-target="#editStaffModal"
                                        data-user-id="<%= u.getUserId() %>"
                                        data-username="<%= u.getUsername() %>"
                                        data-role="<%= u.getRole() %>">
                                    Edit
                                </button>

                                <% if (u.getUserId() != loggedInUser.getUserId()) { %>
                                <form method="post" action="${pageContext.request.contextPath}/manageStaff"
                                      class="d-inline"
                                      onsubmit="return confirm('Remove this staff account? This cannot be undone.');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="userId" value="<%= u.getUserId() %>">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">Remove</button>
                                </form>
                                <% } else { %>
                                <span class="text-muted small">(your account)</span>
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="addStaffModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/manageStaff">
                <input type="hidden" name="action" value="add">
                <div class="modal-header">
                    <h5 class="modal-title">Add Staff Account</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Username</label>
                        <input type="text" class="form-control" name="username" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Password</label>
                        <input type="password" class="form-control" name="password" minlength="4" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Role</label>
                        <select class="form-select" name="role" required>
                            <option value="Receptionist">Receptionist</option>
                            <option value="Admin">Admin</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Add Account</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="editStaffModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/manageStaff">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="userId" id="edit-userId">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Staff Account</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Username</label>
                        <input type="text" class="form-control" name="username" id="edit-username" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">New Password</label>
                        <input type="password" class="form-control" name="password" minlength="4"
                               placeholder="Leave blank to keep current password">
                        <div class="form-text">Leave blank to keep the existing password.</div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Role</label>
                        <select class="form-select" name="role" id="edit-role" required>
                            <option value="Receptionist">Receptionist</option>
                            <option value="Admin">Admin</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('editStaffModal').addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        document.getElementById('edit-userId').value = button.getAttribute('data-user-id');
        document.getElementById('edit-username').value = button.getAttribute('data-username');
        document.getElementById('edit-role').value = button.getAttribute('data-role');
    });
</script>
</body>
</html>