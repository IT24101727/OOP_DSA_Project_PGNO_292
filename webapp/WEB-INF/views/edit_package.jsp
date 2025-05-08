<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.travelagency.model.TravelPackage" %>
<%
    TravelPackage pkg = (TravelPackage) request.getAttribute("package");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Travel Package</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Edit Package</h2>
    <form action="/packages/update" method="post" class="bg-white p-4 rounded shadow">
        <input type="hidden" name="id" value="<%= pkg.getId() %>">
        <div class="mb-3">
            <label>Name:</label>
            <input type="text" name="name" class="form-control" value="<%= pkg.getName() %>">
        </div>
        <div class="mb-3">
            <label>Destination:</label>
            <input type="text" name="destination" class="form-control" value="<%= pkg.getDestination() %>">
        </div>
        <div class="mb-3">
            <label>Duration:</label>
            <input type="number" name="duration" class="form-control" value="<%= pkg.getDuration() %>">
        </div>
        <div class="mb-3">
            <label>Cost:</label>
            <input type="number" name="cost" step="0.01" class="form-control" value="<%= pkg.getCost() %>">
        </div>
        <div class="mb-3">
            <label>Type:</label>
            <select name="type" class="form-select">
                <option value="Domestic" <%= pkg.getType().equals("Domestic") ? "selected" : "" %>>Domestic</option>
                <option value="International" <%= pkg.getType().equals("International") ? "selected" : "" %>>International</option>
            </select>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-warning">Update</button>
        </div>
    </form>
</div>
</body>
</html>
