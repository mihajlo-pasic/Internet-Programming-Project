package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import dao.UserDAO;
import dto.Korisnik;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("korisnik") == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        JSONObject korisnikJSON = (JSONObject) session.getAttribute("korisnik");
        String korisnickoIme = korisnikJSON.getString("korisnickoIme");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        // Provera stare lozinke iz baze
        Korisnik korisnikIzBaze = UserDAO.selectByUsernameAndPassword(korisnickoIme, oldPassword);

        if (korisnikIzBaze != null) {
            // Ako je stara lozinka tačna, ažuriraj novu lozinku
            korisnikIzBaze.setLozinka(newPassword);
            boolean updated = UserDAO.updatePassword(korisnikIzBaze);

            if (updated) {
                request.setAttribute("success", "Lozinka uspešno promenjena.");
            } else {
                request.setAttribute("error", "Greška prilikom promene lozinke.");
            }
        } else {
            request.setAttribute("error", "Stara lozinka nije ispravna.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/profil.jsp");
        dispatcher.forward(request, response);
    }
}
