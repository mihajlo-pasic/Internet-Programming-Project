package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RentalDAO;
import dao.VehicleDAO;
import dto.Iznajmljivanje;
import org.json.JSONObject;

@WebServlet("/StartRentalServlet")
public class StartRentalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("korisnik") == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        JSONObject korisnikJSON = (JSONObject) session.getAttribute("korisnik");
        int korisnikId = korisnikJSON.getInt("id");

        String identifikator = request.getParameter("identifikator");
        String tip = request.getParameter("tip");
        String platnaKartica = request.getParameter("platnaKartica");
        String identifikacioniDokument = request.getParameter("identifikacioniDokument");
        String vozackaDozvola = request.getParameter("vozackaDozvola");

        // Postavljanje trenutnog vremena kao datum početka
        LocalDateTime now = LocalDateTime.now();
        Timestamp datumPocetka = Timestamp.valueOf(now);

        // Kreiranje zapisa o iznajmljivanju u bazi
        int iznajmljivanjeId = RentalDAO.startRental(identifikator, korisnikId, datumPocetka, platnaKartica, identifikacioniDokument, vozackaDozvola);

        if (iznajmljivanjeId > 0) {
            // Ažuriranje statusa vozila na IZNAJMLJENO
            VehicleDAO.updateVehicleStatus(identifikator, "IZNAJMLJENO");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String datumPocetkaStr = now.format(formatter);
            // Preusmeravanje na stranicu za praćenje vožnje
            response.sendRedirect("TrackRentalServlet?iznajmljivanjeId=" + iznajmljivanjeId + "&tip=" + tip +"&datumPocetka="+datumPocetkaStr);
        } else {
            response.sendRedirect("AvailableVehiclesServlet?tip=" + tip);
        }
    }
}
