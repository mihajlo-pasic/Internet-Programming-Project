package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.VehicleDAO;
import dto.PrevoznoSredstvo;

@WebServlet("/AvailableVehiclesServlet")
public class AvailableVehiclesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("korisnik") == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String tip = request.getParameter("tip");
        if (tip == null || tip.isEmpty()) {
            response.sendRedirect("SelectVehicleTypeServlet");
            return;
        }

        List<PrevoznoSredstvo> vozila = VehicleDAO.getAvailableVehiclesByType(tip);
        request.setAttribute("vozila", vozila);
        request.setAttribute("tip", tip);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/available_vehicles.jsp");
        dispatcher.forward(request, response);
    }
}
