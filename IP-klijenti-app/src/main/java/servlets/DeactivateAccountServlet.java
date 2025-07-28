package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import dao.UserDAO;

@WebServlet("/DeactivateAccountServlet")
public class DeactivateAccountServlet extends HttpServlet {
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

        boolean deactivated = UserDAO.deactivateAccount(korisnikId);

        if (deactivated) {
            session.invalidate();  // Invalida sesiju nakon uspešne deaktivacije
            response.sendRedirect("LoginServlet");
        } else {
            request.setAttribute("error", "Greška prilikom deaktivacije naloga.");
            request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
        }
    }
}
