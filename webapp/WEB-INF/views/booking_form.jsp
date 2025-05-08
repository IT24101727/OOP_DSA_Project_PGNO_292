<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.travelagency.model.TravelPackage, com.travelagency.model.Booking" %>
<%
    List<TravelPackage> availablePackages = (List<TravelPackage>) request.getAttribute("packages");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Form</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Travel Booking Form</h2>
    <form action="/booking/create" method="post" class="bg-white p-4 rounded shadow">
        <div class="mb-3">
            <label>Travel Package:</label>
            <select name="packageId" class="form-select" required>
                <option value="">Select a Package</option>
                <% for (TravelPackage pkg : availablePackages) { %>
                    <option value="<%= pkg.getId() %>"><%= pkg.getName() %></option>
                <% } %>
            </select>
        </div>
        <div class="mb-3">
            <label>Your Name:</label>
            <input type="text" name="userName" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Email:</label>
            <input type="email" name="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Number of Travelers:</label>
            <input type="number" name="travelersCount" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Travel Dates:</label>
            <input type="date" name="travelDate" class="form-control" required>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-primary">Book Now</button>
        </div>
    </form>
</div>
</body>
</html>
