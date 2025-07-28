package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dto.Korisnik;
import org.json.JSONObject;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("korisnik") != null) {
            response.sendRedirect("DashboardServlet");
            return;
        }

        String korisnickoIme = request.getParameter("korisnicko_ime");
        String lozinka = request.getParameter("lozinka");

        // Provera korisničkog imena i lozinke
        Korisnik korisnik = UserDAO.selectByUsernameAndPassword(korisnickoIme, lozinka);

//        if (korisnik != null && korisnik.getUloga().equals("KLIJENT") && !korisnik.isBlokiran()) {
//            // Postavljanje korisnika u sesiju
//            session = request.getSession();
//            JSONObject korisnikJSON = new JSONObject();
//            korisnikJSON.put("id", korisnik.getId());
//            korisnikJSON.put("uloga", korisnik.getUloga());
//            korisnikJSON.put("korisnickoIme", korisnik.getKorisnickoIme());
//            korisnikJSON.put("ime", korisnik.getIme());
//            korisnikJSON.put("prezime", korisnik.getPrezime());
//            korisnikJSON.put("email", korisnik.getEmail());
//
//            session.setAttribute("korisnik", korisnikJSON);
//
//            // Preusmeravanje na dashboard
//            response.sendRedirect("DashboardServlet");
//        } else {
//            // Ako su podaci pogrešni ili korisnik nije KLIJENT
//            String errorMessage = "Pogrešno korisničko ime ili lozinka.";
//            if (korisnik != null && korisnik.isBlokiran()) {
//                errorMessage = "Vaš nalog je blokiran. Kontaktirajte podršku.";
//            } else if (korisnik != null && !korisnik.getUloga().equals("KLIJENT")) {
//                errorMessage = "Samo korisnici sa ulogom KLIJENT mogu da se prijave.";
//            }
//
//            request.setAttribute("error", errorMessage);
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/login.jsp");
//            dispatcher.forward(request, response);
//        }
        if (korisnik != null) {
            if (korisnik.isBlokiran()) {
                // Ako je korisnik blokiran
                request.setAttribute("error", "Vaš nalog je blokiran. Kontaktirajte podršku.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/login.jsp");
                dispatcher.forward(request, response);
            } else if (korisnik.getUloga().equals("KLIJENT")) {
                // Ako je korisnik KLIJENT i nije blokiran, postavi sesiju i prijavi ga
                session = request.getSession();
                JSONObject korisnikJSON = new JSONObject();
                korisnikJSON.put("id", korisnik.getId());
                korisnikJSON.put("uloga", korisnik.getUloga());
                korisnikJSON.put("korisnickoIme", korisnik.getKorisnickoIme());
                korisnikJSON.put("ime", korisnik.getIme());
                korisnikJSON.put("prezime", korisnik.getPrezime());
                korisnikJSON.put("email", korisnik.getEmail());

                session.setAttribute("korisnik", korisnikJSON);
                response.sendRedirect("DashboardServlet");
            } else {
                // Ako korisnik nije KLIJENT
                request.setAttribute("error", "Samo korisnici sa ulogom KLIJENT mogu da se prijave.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/login.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            // Ako korisnik nije pronađen (pogrešno korisničko ime ili lozinka)
            request.setAttribute("error", "Pogrešno korisničko ime ili lozinka.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Omogućava direktan pristup login stranici pomoću GET zahteva
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("korisnik") != null) {
            // Ako je korisnik već prijavljen, preusmeri ga na dashboard
            response.sendRedirect("DashboardServlet");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
