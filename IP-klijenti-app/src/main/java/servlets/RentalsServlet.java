package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

import dao.RentalDAO;
import dto.Iznajmljivanje;

@WebServlet("/RentalsServlet")
public class RentalsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("korisnik") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        JSONObject korisnikJSON = (JSONObject) session.getAttribute("korisnik");
        int korisnikId = korisnikJSON.getInt("id");

        List<Iznajmljivanje> iznajmljivanja = RentalDAO.getRentalsByUserId(korisnikId);
        JSONArray jsonArray = new JSONArray();

        for (Iznajmljivanje rental : iznajmljivanja) {
            JSONObject rentalJSON = new JSONObject();
            rentalJSON.put("identifikator", rental.getIdentifikator());
            rentalJSON.put("datumPocetka", rental.getDatumPocetka());
            rentalJSON.put("trajanje", rental.getTrajanje());
            rentalJSON.put("cijena", rental.getCijena());
            rentalJSON.put("platnaKartica", rental.getPlatnaKartica());

            System.out.println("Rental JSON: " + rentalJSON.toString());  // Dodaj ovu liniju za proveru

            jsonArray.put(rentalJSON);
        }


        response.getWriter().write(jsonArray.toString());
    }
}
