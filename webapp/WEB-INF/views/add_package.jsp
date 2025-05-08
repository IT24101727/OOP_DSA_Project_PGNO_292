<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Travel Package</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Add New Travel Package</h2>
    <form action="/packages/add" method="post" class="bg-white p-4 rounded shadow">
        <div class="mb-3">
            <label>Package ID:</label>
            <input type="text" name="id" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Name:</label>
            <input type="text" name="name" class="form-control" required>
        </div>
        <div class="mb-3">
            <label>Destination:</label>
            <input type="text" name="destination" class="form-control">
        </div>
        <div class="mb-3">
            <label>Duration (in days):</label>
            <input type="number" name="duration" class="form-control">
        </div>
        <div class="mb-3">
            <label>Cost (USD):</label>
            <input type="number" step="0.01" name="cost" class="form-control">
        </div>
        <div class="mb-3">
            <label>Package Type:</label>
            <select name="type" class="form-select">
                <option value="Domestic">Domestic</option>
                <option value="International">International</option>
            </select>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-primary">Add Package</button>
        </div>
    </form>
</div>
</body>
</html>
