package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.PrevoznoSredstvo;

public class VehicleDAO {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_GET_AVAILABLE_VEHICLES = 
        "SELECT ps.identifikator, p.naziv AS proizvodjac, ps.model, ps.opis, ps.trenutna_lokacija " +
        "FROM prevozna_sredstva ps " +
        "JOIN proizvodjaci p ON ps.proizvodjac_id = p.id " +
        "WHERE ps.tip = ? AND ps.status = 'SLOBODNO'";

    public static List<PrevoznoSredstvo> getAvailableVehiclesByType(String tip) {
        List<PrevoznoSredstvo> vozila = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_GET_AVAILABLE_VEHICLES, false, tip);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PrevoznoSredstvo vozilo = new PrevoznoSredstvo(
                    rs.getString("identifikator"),
                    rs.getString("proizvodjac"),
                    rs.getString("model"),
                    rs.getString("opis"),
                    rs.getString("trenutna_lokacija")
                );
                vozila.add(vozilo);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return vozila;
    }
    
    public static PrevoznoSredstvo getVehicleByIdentifikator(String identifikator) {
        Connection connection = null;
        ResultSet rs = null;
        PrevoznoSredstvo vozilo = null;

        try {
            connection = connectionPool.checkOut();
            String sql = "SELECT ps.identifikator, p.naziv AS proizvodjac, ps.model, ps.opis, ps.trenutna_lokacija " +
                         "FROM prevozna_sredstva ps " +
                         "JOIN proizvodjaci p ON ps.proizvodjac_id = p.id " +
                         "WHERE ps.identifikator = ?";
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, sql, false, identifikator);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                vozilo = new PrevoznoSredstvo(
                    rs.getString("identifikator"),
                    rs.getString("proizvodjac"),
                    rs.getString("model"),
                    rs.getString("opis"),
                    rs.getString("trenutna_lokacija")
                );
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return vozilo;
    }
    
    public static boolean updateVehicleStatus(String identifikator, String status) {
        Connection connection = null;
        boolean updated = false;

        try {
            connection = connectionPool.checkOut();
            String sql = "UPDATE prevozna_sredstva SET status = ? WHERE identifikator = ?";
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, sql, false, status, identifikator);
            int affectedRows = pstmt.executeUpdate();
            updated = affectedRows > 0;
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return updated;
    }
    
    public static void updateVehicleStatusAndLocation(int iznajmljivanjeId, String status, String lokacijaVracanja) {
        Connection connection = null;

        try {
            connection = connectionPool.checkOut();

            // Pronađi ID vozila iz iznajmljivanja
            String getVehicleIdSql = "SELECT prevozno_sredstvo_id FROM iznajmljivanja WHERE id = ?";
            PreparedStatement getVehicleStmt = DAOUtil.prepareStatement(connection, getVehicleIdSql, false, iznajmljivanjeId);
            ResultSet rs = getVehicleStmt.executeQuery();

            int prevoznoSredstvoId = -1;
            if (rs.next()) {
                prevoznoSredstvoId = rs.getInt("prevozno_sredstvo_id");
            }

            // Ažuriraj status i trenutnu lokaciju vozila
            if (prevoznoSredstvoId != -1) {
                String updateSql = "UPDATE prevozna_sredstva SET status = ?, trenutna_lokacija = ? WHERE id = ?";
                PreparedStatement updateStmt = DAOUtil.prepareStatement(connection, updateSql, false, status, lokacijaVracanja, prevoznoSredstvoId);
                updateStmt.executeUpdate();
                updateStmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
    }




}
