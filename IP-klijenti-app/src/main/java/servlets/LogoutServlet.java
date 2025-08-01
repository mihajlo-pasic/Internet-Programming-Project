package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalida sesija kako bi se korisnik odjavio
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Preusmeravanje na login stranicu nakon odjave
        response.sendRedirect("LoginServlet");
    }
}
