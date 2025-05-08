<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.travelagency.model.User" %>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Manage Users</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Manage Users</h2>
    <a href="/admin/add-user" class="btn btn-success mb-4">Add New User</a>
    <table class="table table-bordered bg-white shadow">
        <thead class="table-light">
        <tr>
            <th>User ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (User user : users) { %>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getUsername() %></td>
                <td><%= user.getEmail() %></td>
                <td>
                    <a href="/admin/edit-user/<%= user.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                    <a href="/admin/delete-user/<%= user.getId() %>" class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
