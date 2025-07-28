<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%
    JSONObject korisnik = (JSONObject) session.getAttribute("korisnik");
    if (korisnik == null || !korisnik.getString("uloga").equals("KLIJENT")) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="d-flex">
        <nav class="bg-dark text-white p-3" style="width: 200px; height: 100vh;">
            <h4>Meni</h4>
            <ul class="nav flex-column">
                <li class="nav-item"><a class="nav-link text-white" href="SelectVehicleTypeServlet">Iznajmljivanje</a></li>
        		<li class="nav-item"><a class="nav-link text-white" href="ProfileServlet">Profil</a></li>
                <li class="nav-item mt-4"><a class="nav-link text-danger" href="LogoutServlet">Odjavi se</a></li>
            </ul>
        </nav>

        <div class="container-fluid p-4">
            <h2>Dobrodošli, <%= korisnik.getString("ime") %>!</h2>
            <p>Izaberite jednu od opcija sa menija za nastavak.</p>
        </div>
    </div>
</body>
</html>
