<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Welcome to the Admin Dashboard</h2>
    <div class="bg-white p-4 rounded shadow">
        <h4>Admin Actions:</h4>
        <ul>
            <li><a href="/admin/manage-users" class="btn btn-info mb-3">Manage Users</a></li>
            <li><a href="/admin/manage-packages" class="btn btn-info mb-3">Manage Travel Packages</a></li>
            <li><a href="/admin/view-bookings" class="btn btn-info mb-3">View All Bookings</a></li>
            <li><a href="/admin/logout" class="btn btn-danger">Logout</a></li>
        </ul>
    </div>
</div>
</body>
</html>
