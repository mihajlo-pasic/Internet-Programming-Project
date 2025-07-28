<%@page import="java.util.stream.Collectors"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, org.json.*" %>
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
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="d-flex">
        <nav class="bg-dark text-white p-3" style="width: 200px; height: 100vh;">
            <h4>Meni</h4>
            <ul class="nav flex-column">
                <li class="nav-item"><a class="nav-link text-white" href="dashboard.jsp">Promocije i Objave</a></li>
                <li class="nav-item"><a class="nav-link text-white" href="nova_objava.jsp">Nova Objava</a></li>
                <li class="nav-item"><a class="nav-link text-white" href="nova_promocija.jsp">Nova Promocija</a></li>
                <li class="nav-item mt-2"><a class="nav-link text-white" href="http://localhost:8080/api/rss" target="_blank">RSS Feed</a></li>
        		<li class="nav-item mt-4"><a class="nav-link text-danger" href="LogoutServlet">Odjavi se</a></li>
            </ul>
        </nav>

        <div class="container-fluid p-4">
            <h2>Promocije i Objave</h2>
            <form class="d-flex mb-3" method="get" action="dashboard.jsp">
                <input class="form-control me-2" type="search" name="keyword" placeholder="Pretraga po naslovu..." aria-label="Search">
                <button class="btn btn-outline-primary" type="submit">Pretraži</button>
            </form>

            <!-- Prikaz promocija i objava -->
            <%
                String keyword = request.getParameter("keyword");
                String apiUrl = (keyword != null && !keyword.isEmpty()) 
                    ? "http://localhost:8080/api/promocije/pretraga?keyword=" + keyword 
                    : "http://localhost:8080/api/promocije";

                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == 200) {
                    InputStream responseStream = conn.getInputStream();
                    String jsonResponse = new BufferedReader(new InputStreamReader(responseStream)).lines().collect(Collectors.joining("\n"));
                    JSONArray promocije = new JSONArray(jsonResponse);
            %>
            
            <%
    			// API poziv za objave
    			String apiObjaveUrl = (keyword != null && !keyword.isEmpty()) 
    			    ? "http://localhost:8080/api/objave/pretraga?keyword=" + keyword 
    			    : "http://localhost:8080/api/objave";

    			URL objaveUrl = new URL(apiObjaveUrl);
    			HttpURLConnection objaveConn = (HttpURLConnection) objaveUrl.openConnection();
    			objaveConn.setRequestMethod("GET");

    			JSONArray objave = new JSONArray();
    			if (objaveConn.getResponseCode() == 200) {
    			    InputStream objaveResponseStream = objaveConn.getInputStream();
    			    String objaveJsonResponse = new BufferedReader(new InputStreamReader(objaveResponseStream))
    			                                    .lines().collect(Collectors.joining("\n"));
    			    objave = new JSONArray(objaveJsonResponse);
    			}
			%>
			<%
    			int pagee = 1; // Defaultna stranica
    			int itemsPerPage = 5; // Broj stavki po stranici

    			if (request.getParameter("page") != null) {
    			    pagee = Integer.parseInt(request.getParameter("page"));
    			}

    			int startIndex = (pagee - 1) * itemsPerPage; // Prvi element na stranici
    			int endIndex = startIndex + itemsPerPage;   // Poslednji element na stranici
    			
    			// Računanje ukupnog broja stranica
    		    int totalItems = promocije.length() + objave.length();
    		    int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
			%>
			

<table class="table table-bordered">
    <thead>
        <tr>
            <th>Naslov</th>
            <th>Opis/Sadržaj</th>
            <th>Datum Trajanja/Kreiranja</th>
            <th>Akcije</th>
        </tr>
    </thead>
    <tbody>
        <%-- Prikaz promocija sa paginacijom --%>
        <%
            int totalPromocije = promocije.length();
            for (int i = startIndex; i < Math.min(endIndex, totalPromocije); i++) {
                JSONObject promocija = promocije.getJSONObject(i);
        %>
        <tr>
            <td><%= promocija.getString("naslov") %></td>
            <td><%= promocija.getString("opis") %></td>
            <td><%= promocija.getString("datumPocetka") %> - <%= promocija.getString("datumZavrsetka") %></td>
            <td><a href="deletePromocija?id=<%= promocija.getInt("id") %>" class="text-danger">Obriši</a></td>
        </tr>
        <% } %>

        <%-- Prikaz objava sa paginacijom --%>
        <%
            int totalObjave = objave.length();
            for (int i = startIndex; i < Math.min(endIndex, totalObjave); i++) {
                JSONObject objava = objave.getJSONObject(i);
        %>
        <tr>
            <td><%= objava.getString("naslov") %></td>
            <td><%= objava.getString("sadrzaj") %></td>
            <td><%= objava.getString("datumKreiranja") %></td>
            <td><a href="deleteObjava?id=<%= objava.getInt("id") %>" class="text-danger">Obriši</a></td>
        </tr>
        <% } %>
    </tbody>
</table>

<%-- Paginacija --%>
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <% if (pagee > 1) { %>
            <li class="page-item">
                <a class="page-link" href="dashboard.jsp?page=<%= pagee - 1 %>" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        <% } else { %>
            <li class="page-item disabled">
                <span class="page-link" aria-hidden="true">&laquo;</span>
            </li>
        <% } %>

        <% for (int i = 1; i <= totalPages; i++) { %>
            <li class="page-item <%= (i == pagee) ? "active" : "" %>">
                <a class="page-link" href="dashboard.jsp?page=<%= i %>"><%= i %></a>
            </li>
        <% } %>

        <% if (pagee < totalPages) { %>
            <li class="page-item">
                <a class="page-link" href="dashboard.jsp?page=<%= pagee + 1 %>" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        <% } else { %>
            <li class="page-item disabled">
                <span class="page-link" aria-hidden="true">&raquo;</span>
            </li>
        <% } %>
    </ul>
</nav>

            <%
                } else {
            %>
            <p class="text-danger">Greška pri učitavanju promocija.</p>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>
