package servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class CreatePromocijaServlet
 */
@WebServlet("/CreatePromocijaServlet")
public class CreatePromocijaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePromocijaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String naslov = request.getParameter("naslov");
        String opis = request.getParameter("opis");
        String datumPocetka = request.getParameter("datumPocetka");
        String datumZavrsetka = request.getParameter("datumZavrsetka");

        JSONObject promocijaJson = new JSONObject();
        promocijaJson.put("naslov", naslov);
        promocijaJson.put("opis", opis);
        promocijaJson.put("datumPocetka", datumPocetka);
        promocijaJson.put("datumZavrsetka", datumZavrsetka);

        URL url = new URL("http://localhost:8080/api/promocije");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = promocijaJson.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK || conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("nova_promocija.jsp?error=server");
        }
    }

}
