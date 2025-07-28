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
    <title>Nova Objava</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow" style="width: 400px;">
            <h2 class="text-center mb-4">Nova Objava</h2>
            <form action="CreateObjavaServlet" method="post">
                <div class="mb-3">
                    <label for="naslov" class="form-label">Naslov</label>
                    <input type="text" class="form-control" id="naslov" name="naslov" required>
                </div>
                <div class="mb-3">
                    <label for="sadrzaj" class="form-label">Sadržaj</label>
                    <textarea class="form-control" id="sadrzaj" name="sadrzaj" rows="4" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary w-100">Kreiraj Objavu</button>
            </form>
        </div>
    </div>
</body>
</html>
