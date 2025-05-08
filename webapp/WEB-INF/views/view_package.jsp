<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.travelagency.model.TravelPackage" %>
<%
    TravelPackage pkg = (TravelPackage) request.getAttribute("package");
%>
<!DOCTYPE html>
<html>
<head>
    <title>View Travel Package</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Package Details</h2>
    <div class="bg-white p-4 rounded shadow">
        <% if (pkg != null) { %>
            <p><strong>ID:</strong> <%= pkg.getId() %></p>
            <p><strong>Name:</strong> <%= pkg.getName() %></p>
            <p><strong>Destination:</strong> <%= pkg.getDestination() %></p>
            <p><strong>Duration:</strong> <%= pkg.getDuration() %> days</p>
            <p><strong>Cost:</strong> $<%= pkg.getCost() %></p>
            <p><strong>Type:</strong> <%= pkg.getType() %></p>
        <% } else { %>
            <p class="text-danger">Package not found.</p>
        <% } %>
    </div>
</div>
</body>
</html>
