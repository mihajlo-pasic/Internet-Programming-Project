<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
    String iznajmljivanjeId = (String) request.getSession().getAttribute("iznajmljivanjeId");
    String tip = (String) request.getSession().getAttribute("tip");
    Integer cijenaIznajmljivanja = (Integer) request.getSession().getAttribute("cijena_iznajmljivanja");
    
 // Dohvati početno vreme iz sesije
    LocalDateTime startTime = (LocalDateTime) request.getSession().getAttribute("start_time");

    // Formatiraj vreme za prikaz (HH:mm:ss)
    String formattedStartTime = (startTime != null) 
        ? startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) 
        : "Vreme nije dostupno";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Praćenje vožnje</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container text-center">
        <h2>Vožnja je u toku...</h2>
         <p>Početno vreme vožnje: <strong><%= formattedStartTime %></strong></p>
        <form id="endRentalForm" action="EndRentalServlet" method="post">
            <input type="hidden" name="iznajmljivanjeId" value="<%= iznajmljivanjeId %>">
            <input type="hidden" name="tip" value="<%= tip %>">
            <button type="submit" class="btn btn-danger w-100">Završi vožnju</button>
        </form>
        <p>Cena po sekundi: <%= cijenaIznajmljivanja %> RSD</p>
    </div>
</body>
</html>
