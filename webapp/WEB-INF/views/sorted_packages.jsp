<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.travelagency.model.TravelPackage" %>
<%
    List<TravelPackage> sortedPackages = (List<TravelPackage>) request.getAttribute("sortedPackages");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Sorted Travel Packages</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Sorted Travel Packages by Price</h2>
    <table class="table table-bordered bg-white shadow">
        <thead class="table-light">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Destination</th>
            <th>Duration</th>
            <th>Cost</th>
            <th>Type</th>
        </tr>
        </thead>
        <tbody>
        <% for (TravelPackage pkg : sortedPackages) { %>
            <tr>
                <td><%= pkg.getId() %></td>
                <td><%= pkg.getName() %></td>
                <td><%= pkg.getDestination() %></td>
                <td><%= pkg.getDuration() %> days</td>
                <td>$<%= pkg.getCost() %></td>
                <td><%= pkg.getType() %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
