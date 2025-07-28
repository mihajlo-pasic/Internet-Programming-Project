<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%
    JSONObject korisnik = (JSONObject) session.getAttribute("korisnik");
    if (korisnik == null || !korisnik.getString("uloga").equals("MENADZER")) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nova Promocija</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow" style="width: 400px;">
            <h2 class="text-center mb-4">Nova Promocija</h2>
            <form action="CreatePromocijaServlet" method="post">
                <div class="mb-3">
                    <label for="naslov" class="form-label">Naslov</label>
                    <input type="text" class="form-control" id="naslov" name="naslov" required>
                </div>
                <div class="mb-3">
                    <label for="opis" class="form-label">Opis</label>
                    <textarea class="form-control" id="opis" name="opis" rows="4" required></textarea>
                </div>
                <div class="mb-3">
                    <label for="datumPocetka" class="form-label">Datum Pocetka</label>
                    <input type="date" class="form-control" id="datumPocetka" name="datumPocetka" required>
                </div>
                <div class="mb-3">
                    <label for="datumZavrsetka" class="form-label">Datum Zavrsšetka</label>
                    <input type="date" class="form-control" id="datumZavrsetka" name="datumZavrsetka" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Kreiraj Promociju</button>
            </form>
        </div>
    </div>
</body>
</html>
