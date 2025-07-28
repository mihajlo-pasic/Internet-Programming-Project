package servlets;

import dao.DAOUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import dao.ConnectionPool;

@WebServlet("/GetRentalPriceServlet")
public class GetRentalPriceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tip = request.getParameter("tip");  // Dobijanje tipa vozila iz URL parametara
        System.out.println("Primljen tip u GetRentalPriceServlet: " + tip);
        if (tip == null || tip.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tip vozila nije prosleđen.");
            return;
        }

        int cijenaIznajmljivanja = getCijenaIznajmljivanja(tip);
        System.out.println("Dohvaćena cijena iz baze: " + cijenaIznajmljivanja); 
        
        // Vraćanje JSON odgovora
        response.setHeader("Access-Control-Allow-Origin", "*");  // Dozvoli sve domene (samo za razvoj)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("cijena_iznajmljivanja", cijenaIznajmljivanja);

        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

    private int getCijenaIznajmljivanja(String tip) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        Connection connection = null;
        ResultSet rs = null;
        int cijena = 0;  // Podrazumevana cena ako nije pronađena

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
