<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.travelagency.model.Booking" %>
<%
    List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
%>
<!DOCTYPE html>
<html>
<head>
    <title>All Bookings</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">All Travel Bookings</h2>
    <table class="table table-bordered bg-white shadow">
        <thead class="table-light">
        <tr>
            <th>Booking ID</th>
            <th>Package Name</th>
            <th>User Name</th>
            <th>Email</th>
            <th>Travel Date</th>
            <th>Number of Travelers</th>
            <th>Total Cost</th>
        </tr>
        </thead>
        <tbody>
        <% for (Booking booking : bookings) { %>
            <tr>
                <td><%= booking.getId() %></td>
                <td><%= booking.getPackageName() %></td>
                <td><%= booking.getUserName() %></td>
                <td><%= booking.getEmail() %></td>
                <td><%= booking.getTravelDate() %></td>
                <td><%= booking.getTravelersCount() %></td>
                <td>$<%= booking.getTotalCost() %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
