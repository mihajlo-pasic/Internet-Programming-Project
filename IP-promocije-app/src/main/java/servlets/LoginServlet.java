package servlets;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String korisnickoIme = request.getParameter("username");
        String lozinka = request.getParameter("password");

        // Poziv API-ja za autentifikaciju (Spring Boot na portu 8080)
        try {
            URL url = new URL("http://localhost:8080/api/korisnici/korisnicko-ime/" + korisnickoIme);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                InputStream responseStream = conn.getInputStream();
                String jsonResponse = new BufferedReader(new InputStreamReader(responseStream)).lines().collect(Collectors.joining("\n"));
                JSONObject korisnik = new JSONObject(jsonResponse);

                if (korisnik.getString("lozinka").equals(lozinka) && korisnik.getString("uloga").equals("MENADZER")) {
                    HttpSession session = request.getSession();
                    session.setAttribute("korisnik", korisnik);
                    response.sendRedirect("dashboard.jsp");
                } else {
                    response.sendRedirect("login.jsp?error=invalid");
                }
            } else {
                response.sendRedirect("login.jsp?error=invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=server");
        }
    }

}
