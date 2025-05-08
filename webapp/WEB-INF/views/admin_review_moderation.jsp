<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.travelagency.model.Review" %>
<%
    List<Review> reviews = (List<Review>) request.getAttribute("reviews");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Review Moderation</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Moderate Reviews</h2>
    <table class="table table-bordered bg-white shadow">
        <thead class="table-light">
        <tr>
            <th>User Name</th>
            <th>Review</th>
            <th>Rating</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Review review : reviews) { %>
            <tr>
                <td><%= review.getUserName() %></td>
                <td><%= review.getReviewText() %></td>
                <td><%= review.getRating() %></td>
                <td>
                    <a href="/admin/delete-review/<%= review.getId() %>" class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
