package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.VehicleDAO;
import dto.PrevoznoSredstvo;

@WebServlet("/SelectVehicleServlet")
public class SelectVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("korisnik") == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String identifikator = request.getParameter("identifikator");
        String tip = request.getParameter("tip");

        if (identifikator == null || tip == null) {
            response.sendRedirect("AvailableVehiclesServlet?tip=" + tip);
            return;
        }

        PrevoznoSredstvo vozilo = VehicleDAO.getVehicleByIdentifikator(identifikator);

        request.setAttribute("vozilo", vozilo);
        request.setAttribute("tip", tip);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/vehicle_details.jsp");
        dispatcher.forward(request, response);
    }
}
