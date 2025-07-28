<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registracija</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow" style="width: 400px;">
            <h2 class="text-center mb-4">Registracija</h2>
            <form action="RegisterServlet" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="ime" class="form-label">Ime</label>
                    <input type="text" class="form-control" id="ime" name="ime" required>
                </div>
                <div class="mb-3">
                    <label for="prezime" class="form-label">Prezime</label>
                    <input type="text" class="form-control" id="prezime" name="prezime" required>
                </div>
                <div class="mb-3">
                    <label for="korisnickoIme" class="form-label">Korisničko Ime</label>
                    <input type="text" class="form-control" id="korisnickoIme" name="korisnickoIme" required>
                </div>
                <div class="mb-3">
                    <label for="lozinka" class="form-label">Lozinka</label>
                    <input type="password" class="form-control" id="lozinka" name="lozinka" required>
                </div>
                <div class="mb-3">
                    <label for="brojLicneKarte" class="form-label">Broj Lične Karte</label>
                    <input type="text" class="form-control" id="brojLicneKarte" name="brojLicneKarte" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="brojTelefona" class="form-label">Broj Telefona</label>
                    <input type="text" class="form-control" id="brojTelefona" name="brojTelefona">
                </div>
                <div class="mb-3">
                    <label for="avatar" class="form-label">Avatar (opciono)</label>
                    <input type="file" class="form-control" id="avatar" name="avatar">
                </div>
                <button type="submit" class="btn btn-primary w-100">Registruj se</button>
            </form>
            <div class="text-center mt-3">
                <a href="LoginServlet">Već imate nalog? Prijavite se</a>
            </div>
            
            <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
<% } %>
            
        </div>
    </div>
</body>
</html>
