<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.json.JSONObject" %>
<%@ page session="true" %>
<%
    JSONObject korisnik = (JSONObject) session.getAttribute("korisnik");
    if (korisnik == null || !korisnik.getString("uloga").equals("KLIJENT")) {
        response.sendRedirect("LoginServlet");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .container { max-width: 600px; margin-top: 20px; }
        .back-to-dashboard {
            position: absolute;
            top: 20px;
            left: 20px;
        }
        .table-container {
            max-height: 300px;
            overflow-y: auto;
        }
        td {
    color: black !important;
}
        
    </style>
</head>
<body>
    <!-- Dugme za povratak na Dashboard -->
    <a href="DashboardServlet" class="btn btn-secondary back-to-dashboard">← Povratak</a>

    <div class="container">
        <h2 class="text-center">Profil korisnika</h2>
        
        <!-- Prikaz uspešne ili neuspešne poruke -->
        <%
            String successMessage = (String) request.getAttribute("success");
            String errorMessage = (String) request.getAttribute("error");

            if (successMessage != null) {
        %>
            <div class="alert alert-success" role="alert"><%= successMessage %></div>
        <% 
            }
            if (errorMessage != null) {
        %>
            <div class="alert alert-danger" role="alert"><%= errorMessage %></div>
        <% 
            } 
        %>
        <div class="card p-3 mt-3 shadow-sm">
            <h4>Informacije o korisniku</h4>
            <p><strong>Ime:</strong> <%= korisnik.getString("ime") %></p>
            <p><strong>Prezime:</strong> <%= korisnik.getString("prezime") %></p>
            <p><strong>Email:</strong> <%= korisnik.getString("email") %></p>
        </div>

        <div class="card p-3 mt-4 shadow-sm">
            <h4>Promena lozinke</h4>
            <form action="ChangePasswordServlet" method="post">
                <div class="mb-3">
                    <label for="oldPassword" class="form-label">Stara lozinka</label>
                    <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
                </div>
                <div class="mb-3">
                    <label for="newPassword" class="form-label">Nova lozinka</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Promeni lozinku</button>
            </form>
        </div>

        <div class="card p-3 mt-4 shadow-sm">
            <h4>Deaktivacija naloga</h4>
            <form action="DeactivateAccountServlet" method="post" onsubmit="return confirm('Da li ste sigurni da želite deaktivirati nalog?');">
                <button type="submit" class="btn btn-danger w-100">Deaktiviraj nalog</button>
            </form>
        </div>

        <div class="card p-3 mt-4 shadow-sm">
            <h4>Vaša iznajmljivanja</h4>
            <div class="table-container">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Identifikator</th>
                            <th>Datum Početka</th>
                            <th>Trajanje</th>
                            <th>Cijena</th>
                            <th>Platna Kartica</th>
                        </tr>
                    </thead>
                    <tbody id="rentalList">
                        <!-- Iznajmljivanja će biti učitana ovde preko AJAX-a -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
    window.onload = function() {
        fetch('RentalsServlet')
            .then(response => response.json())
            .then(data => {
                console.log("Podaci učitani iz RentalsServlet:", data);
                let rentalList = document.getElementById('rentalList');

                if (data.length === 0) {
                    let row = rentalList.insertRow();
                    let cell = row.insertCell(0);
                    cell.colSpan = 5;
                    cell.innerText = 'Nema iznajmljivanja.';
                } else {
                    data.forEach(rental => {
                        console.log("Iznajmljivanje:", rental);

                        let rawDate = rental.datumPocetka.replace(' ', 'T').split('.')[0];
                        let formattedDate = new Date(rawDate).toLocaleString();
                        console.log("Prikazani datum:", formattedDate);

                        let row = rentalList.insertRow();

                        row.insertCell(0).innerText = rental.identifikator;
                        row.insertCell(1).innerText = formattedDate;
                        row.insertCell(2).innerText = rental.trajanje + "s";
                        row.insertCell(3).innerText = rental.cijena + "RSD";
                        row.insertCell(4).innerText = rental.platnaKartica;
                    });
                }
            })
            .catch(error => {
                console.error('Greška prilikom dohvatanja iznajmljivanja:', error);
            });
    };

    </script>
</body>
</html>
