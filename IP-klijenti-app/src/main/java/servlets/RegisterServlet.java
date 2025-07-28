package servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.UserDAO;
import dto.Korisnik;

@WebServlet("/RegisterServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,    // 1 MB
                 maxFileSize = 1024 * 1024 * 5,      // 5 MB
                 maxRequestSize = 1024 * 1024 * 10)  // 10 MB
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ime = request.getParameter("ime");
        String prezime = request.getParameter("prezime");
        String korisnickoIme = request.getParameter("korisnickoIme");
        String lozinka = request.getParameter("lozinka");
        String brojLicneKarte = request.getParameter("brojLicneKarte");
        String email = request.getParameter("email");
        String brojTelefona = request.getParameter("brojTelefona");

        if (UserDAO.isUserNameUsed(korisnickoIme)) {
            request.setAttribute("error", "Korisničko ime je već zauzeto.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Part filePart = request.getPart("avatar");
        String avatarPath = null;
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = korisnickoIme + "_" + filePart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/uploads"); // Ovde dodajemo folder
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            avatarPath = "uploads/" + fileName;
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setUloga("KLIJENT");
        korisnik.setKorisnickoIme(korisnickoIme);
        korisnik.setLozinka(lozinka);
        korisnik.setIme(ime);
        korisnik.setPrezime(prezime);
        korisnik.setBrojLicneKarte(brojLicneKarte);
        korisnik.setEmail(email);
        korisnik.setBrojTelefona(brojTelefona);
        korisnik.setAvatar(avatarPath);
        korisnik.setBlokiran(false);

        boolean success = UserDAO.insert(korisnik);
        if (success) {
            request.setAttribute("success", "Registracija uspešna! Prijavite se.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/login.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("error", "Greška pri registraciji. Pokušajte ponovo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("korisnik") != null) {
            // Ako je korisnik već prijavljen, preusmeri ga na dashboard
            response.sendRedirect("DashboardServlet");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
            dispatcher.forward(request, response);
        }
    }

}
