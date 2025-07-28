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
    <title>Izbor prevoznog sredstva</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .container { max-width: 400px; margin-top: 50px; }
    </style>
</head>
<body>
    <div class="container text-center">
        <h2>Izaberite tip prevoznog sredstva</h2>
        <form action="AvailableVehiclesServlet" method="get">
            <div class="form-group mt-4">
                <select name="tip" class="form-select" required>
                    <option value="">Odaberite...</option>
                    <option value="AUTOMOBIL">Automobil</option>
                    <option value="BICIKL">Bicikl</option>
                    <option value="TROTINET">Trotinet</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Prikaži dostupna vozila</button>
        </form>
    </div>
</body>
</html>
