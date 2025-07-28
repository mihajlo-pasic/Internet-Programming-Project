<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String iznajmljivanjeId = request.getParameter("iznajmljivanjeId");
    String trajanjeUSekundama = request.getParameter("trajanjeUSekundama");
    String ukupnaCena = request.getParameter("ukupnaCena");
    
    System.out.println("return_location.jsp - iznajmljivanjeId: "+ iznajmljivanjeId +", trajanje: " + trajanjeUSekundama + ", cena: " + ukupnaCena);

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Završetak vožnje</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function handleFormSubmit(event) {
            event.preventDefault(); // Sprečava trenutno slanje forme
            
            // Sakrij dugme za završetak vožnje
            document.getElementById('finishButton').style.display = 'none';
            
            // Prikazuj dugme za povratak na dashboard
            document.getElementById('dashboardButton').style.display = 'block';

            // Nakon što se prikaže dugme, šaljemo formu
            event.target.submit();
        }
    </script>    
</head>
<body>
     <div class="container mt-5">
        <h2>Unesite lokaciju vraćanja vozila</h2>
        <form action="EndRentalServlet" method="post" onsubmit="handleFormSubmit(event)">
            <input type="hidden" name="iznajmljivanjeId" value="<%= iznajmljivanjeId %>">
            <input type="hidden" name="trajanjeUSekundama" value="<%= trajanjeUSekundama %>">
            <input type="hidden" name="ukupnaCena" value="<%= ukupnaCena %>">
            
            <div class="mb-3">
                <label for="lokacijaVracanja" class="form-label">Lokacija (format: x,y) (0-19)</label>
                <input type="text" class="form-control" id="lokacijaVracanja" name="lokacijaVracanja" pattern="\d{1,2},\d{1,2}" placeholder="npr. 5,10" required>
            </div>

            <button type="submit" id="finishButton" class="btn btn-primary">Završi vožnju i generiši račun</button>
        </form>
        <a href="DashboardServlet" id="dashboardButton" class="btn btn-success mt-3" style="display: none;">Povratak na Dashboard</a>
        
    </div>
</body>
</html>
