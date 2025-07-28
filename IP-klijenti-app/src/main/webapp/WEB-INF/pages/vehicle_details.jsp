<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.PrevoznoSredstvo" %>
<%@ page session="true" %>
<%
    PrevoznoSredstvo vozilo = (PrevoznoSredstvo) request.getAttribute("vozilo");
    String tip = (String) request.getAttribute("tip");

    if (vozilo == null) {
        response.sendRedirect("AvailableVehiclesServlet?tip=" + tip);
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalji o vozilu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .container { max-width: 600px; margin-top: 30px; }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="text-center mb-4">Detalji o vozilu</h2>

        <div class="card p-3 mb-4 shadow-sm">
            <p><strong>Identifikator:</strong> <%= vozilo.getIdentifikator() %></p>
            <p><strong>Proizvođač:</strong> <%= vozilo.getProizvodjac() %></p>
            <p><strong>Model:</strong> <%= vozilo.getModel() %></p>
            <p><strong>Opis:</strong> <%= vozilo.getOpis() %></p>
            <p><strong>Trenutna lokacija:</strong> <%= vozilo.getTrenutnaLokacija() %></p>
        </div>

        <h4>Unesite informacije za iznajmljivanje</h4>
        <form action="StartRentalServlet" method="post">
            <input type="hidden" name="identifikator" value="<%= vozilo.getIdentifikator() %>">
            <input type="hidden" name="tip" value="<%= tip %>">

            <div class="mb-3">
                <label for="platnaKartica" class="form-label">Platna kartica</label>
                <input type="text" class="form-control" id="platnaKartica" name="platnaKartica" required pattern="\d{16}" placeholder="Unesite 16-cifreni broj kartice">
            </div>

            <% if ("AUTOMOBIL".equals(tip)) { %>
                <div class="mb-3">
                    <label for="identifikacioniDokument" class="form-label">Identifikacioni dokument</label>
                    <input type="text" class="form-control" id="identifikacioniDokument" name="identifikacioniDokument" required placeholder="Unesite broj dokumenta">
                </div>
                <div class="mb-3">
                    <label for="vozackaDozvola" class="form-label">Vozačka dozvola</label>
                    <input type="text" class="form-control" id="vozackaDozvola" name="vozackaDozvola" required placeholder="Unesite broj vozačke dozvole">
                </div>
            <% } %>

            <button type="submit" class="btn btn-primary w-100 mt-3">Započni vožnju</button>
        </form>
    </div>
</body>
</html>
