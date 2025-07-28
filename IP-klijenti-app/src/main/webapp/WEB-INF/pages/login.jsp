<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow" style="width: 400px;">
            <h2 class="text-center mb-4">Prijava</h2>
            <form action="LoginServlet" method="post">
                <div class="mb-3">
                    <label for="korisnicko_ime" class="form-label">Korisničko ime</label>
                    <input type="text" class="form-control" id="korisnicko_ime" name="korisnicko_ime" required>
                </div>
                <div class="mb-3">
                    <label for="lozinka" class="form-label">Lozinka</label>
                    <input type="password" class="form-control" id="lozinka" name="lozinka" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Prijavi se</button>
            </form>
            <div class="text-center mt-3">
                <a href="TestServlet">Nemate nalog? Registrujte se</a>
            </div>
            <% if (request.getAttribute("success") != null) { %>
    <div class="alert alert-success"><%= request.getAttribute("success") %></div>
<% } %>
<% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
<% } %>
            
        </div>
    </div>
</body>
</html>
