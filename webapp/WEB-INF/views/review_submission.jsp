<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.travelagency.model.TravelPackage" %>
<%
    List<TravelPackage> availablePackages = (List<TravelPackage>) request.getAttribute("packages");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Review</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Submit Your Review</h2>
    <form action="/review/submit" method="post" class="bg-white p-4 rounded shadow">
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
            <label>Your Review:</label>
            <textarea name="reviewText" class="form-control" rows="5" required></textarea>
        </div>
        <div class="mb-3">
            <label>Rating:</label>
            <select name="rating" class="form-select" required>
                <option value="1">1 - Poor</option>
                <option value="2">2 - Fair</option>
                <option value="3">3 - Good</option>
                <option value="4">4 - Very Good</option>
                <option value="5">5 - Excellent</option>
            </select>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-primary">Submit Review</button>
        </div>
    </form>
</div>
</body>
</html>
