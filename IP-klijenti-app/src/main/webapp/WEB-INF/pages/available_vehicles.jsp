<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dto.PrevoznoSredstvo" %>
<%@ page session="true" %>
<%
    List<PrevoznoSredstvo> vozila = (List<PrevoznoSredstvo>) request.getAttribute("vozila");
    String tip = (String) request.getAttribute("tip");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dostupna vozila</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .container { max-width: 800px; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="text-center">Dostupna vozila - <%= tip %></h2>

        <table class="table table-striped mt-4">
            <thead>
                <tr>
                    <th>Identifikator</th>
                    <th>Proizvođač</th>
                    <th>Model</th>
                    <th>Opis</th>
                    <th>Lokacija</th>
                    <th>Akcija</th>
                </tr>
            </thead>
            <tbody>
                <% if (vozila != null && !vozila.isEmpty()) {
                    for (PrevoznoSredstvo vozilo : vozila) { %>
                        <tr>
                            <td><%= vozilo.getIdentifikator() %></td>
                            <td><%= vozilo.getProizvodjac() %></td>
                            <td><%= vozilo.getModel() %></td>
                            <td><%= vozilo.getOpis() %></td>
                            <td><%= vozilo.getTrenutnaLokacija() %></td>
                            <td><a href="SelectVehicleServlet?identifikator=<%= vozilo.getIdentifikator() %>&tip=<%= tip %>" class="btn btn-success">Izaberi</a></td>
                        </tr>
                    <% }
                } else { %>
                    <tr><td colspan="6" class="text-center">Nema dostupnih vozila.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
