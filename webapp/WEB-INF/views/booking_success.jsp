<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Successful</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Booking Confirmation</h2>
    <div class="bg-white p-4 rounded shadow">
        <p class="text-success">Your booking was successful!</p>
        <p><strong>Package Name:</strong> <%= request.getAttribute("packageName") %></p>
        <p><strong>Your Name:</strong> <%= request.getAttribute("userName") %></p>
        <p><strong>Email:</strong> <%= request.getAttribute("email") %></p>
        <p><strong>Number of Travelers:</strong> <%= request.getAttribute("travelersCount") %></p>
        <p><strong>Travel Date:</strong> <%= request.getAttribute("travelDate") %></p>
        <p><strong>Total Cost:</strong> $<%= request.getAttribute("totalCost") %></p>
    </div>
</div>
</body>
</html>
