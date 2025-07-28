package servlets;

import dao.DAOUtil;
import dao.ConnectionPool;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TrackRentalServlet")
public class TrackRentalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String iznajmljivanjeId = request.getParameter("iznajmljivanjeId");
        String tip = request.getParameter("tip");
        String datumPocetkaStr = request.getParameter("datumPocetka");
        LocalDateTime datumPocetka = LocalDateTime.parse(datumPocetkaStr);
        
        if (iznajmljivanjeId == null || tip == null) {
            response.sendRedirect("DashboardServlet");
            return;
        }

        // Dohvati cijenu iz baze na osnovu tipa
        int cijenaIznajmljivanja = getCijenaIznajmljivanja(tip);

        // Postavi podatke u sesiju
        request.getSession().setAttribute("iznajmljivanjeId", iznajmljivanjeId);
        request.getSession().setAttribute("tip", tip);
        request.getSession().setAttribute("cijena_iznajmljivanja", cijenaIznajmljivanja);
        request.getSession().setAttribute("start_time", datumPocetka);

        // Prosledi na track_rental.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/track_rental.jsp");
        dispatcher.forward(request, response);
    }

    private int getCijenaIznajmljivanja(String tip) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection connection = null;
        ResultSet rs = null;
        int cijena = 0;  // Podrazumevana cijena ako nije pronađena

        try {
            connection = connectionPool.checkOut();
            String sql = "SELECT cijena_iznajmljivanja FROM cijene_iznajmljivanja WHERE tip = ?";
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, sql, false, tip);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cijena = rs.getInt("cijena_iznajmljivanja");
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return cijena;
    }
}
