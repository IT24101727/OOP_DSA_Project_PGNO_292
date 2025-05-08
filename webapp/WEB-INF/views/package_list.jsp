<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.travelagency.model.TravelPackage" %>
<%
    List<TravelPackage> packages = (List<TravelPackage>) request.getAttribute("packages");
%>
<!DOCTYPE html>
<html>
<head>
    <title>All Packages</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">All Travel Packages</h2>
    <table class="table table-bordered bg-white shadow">
        <thead class="table-light">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Destination</th>
            <th>Duration</th>
            <th>Cost</th>
            <th>Type</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (TravelPackage pkg : packages) { %>
            <tr>
                <td><%= pkg.getId() %></td>
                <td><%= pkg.getName() %></td>
                <td><%= pkg.getDestination() %></td>
                <td><%= pkg.getDuration() %> days</td>
                <td>$<%= pkg.getCost() %></td>
                <td><%= pkg.getType() %></td>
                <td>
                    <a href="/packages/edit?id=<%= pkg.getId() %>" class="btn btn-sm btn-info">Edit</a>
                    <a href="/packages/delete?id=<%= pkg.getId() %>" class="btn btn-sm btn-danger">Delete</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
