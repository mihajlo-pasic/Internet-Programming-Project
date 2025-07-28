package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RentalDAO;
import dao.VehicleDAO;

@WebServlet("/EndRentalServlet")
public class EndRentalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	
    	 HttpSession session = request.getSession(false);
         if (session == null) {
             response.sendRedirect("DashboardServlet");
             return;
         }
    	
    	System.out.println("Primljeni parametri:");
        System.out.println("iznajmljivanjeId: " + session.getAttribute("iznajmljivanjeId"));
        System.out.println("trajanjeUSekundama: " + session.getAttribute("trajanjeUSekundama"));
        System.out.println("ukupnaCena: " + session.getAttribute("ukupnaCena"));
        System.out.println("tip: " + session.getAttribute("tip"));

    	
        String iznajmljivanjeIdStr = (String) session.getAttribute("iznajmljivanjeId");
        int iznajmljivanjeId = Integer.parseInt(iznajmljivanjeIdStr);
        String tip = (String) session.getAttribute("tip");
        LocalDateTime startTime = (LocalDateTime) session.getAttribute("start_time");
        LocalDateTime endTime = LocalDateTime.now();
        Integer cijenaIznajmljivanja = (Integer) session.getAttribute("cijena_iznajmljivanja");

        String lokacijaVracanja = request.getParameter("lokacijaVracanja");
        if (lokacijaVracanja == null || lokacijaVracanja.isEmpty()) {
            response.sendRedirect("ReturnLocationServlet?iznajmljivanjeId=" + iznajmljivanjeId);
            return;
        }

        System.out.println("lokacijaVracanja: " + session.getAttribute("lokacijaVracanja"));
        // Zabeleži trenutni datum kao datum završetka
        //Timestamp datumZavrsetka = Timestamp.valueOf(LocalDateTime.now());

        // Preuzmi informacije o vožnji (trajanje, cena po sekundi)

        if (startTime == null || cijenaIznajmljivanja == null) {
            response.getWriter().println("Greška: Nisu pronađeni podaci o vožnji.");
            return;
        }
        
        long trajanjeUSekundama = Duration.between(startTime, endTime).getSeconds();
        int ukupnaCena = (int) (trajanjeUSekundama * cijenaIznajmljivanja);


        System.out.println("Završetak vožnje - trajanje: " + trajanjeUSekundama + ", cena: " + ukupnaCena);


        // Ažuriraj podatke o iznajmljivanju
        RentalDAO.finishRental(iznajmljivanjeId, Timestamp.valueOf(endTime), (int) trajanjeUSekundama, lokacijaVracanja, ukupnaCena);

        // Ažuriraj status vozila na SLOBODNO i lokaciju vraćanja
        VehicleDAO.updateVehicleStatusAndLocation(iznajmljivanjeId, "SLOBODNO", lokacijaVracanja);

        // Preusmeri na stranicu za prikaz računa
        response.sendRedirect("InvoiceServlet?iznajmljivanjeId=" + iznajmljivanjeId);
    }
}
