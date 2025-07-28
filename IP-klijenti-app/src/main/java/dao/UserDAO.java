package dao;

import java.sql.*;
import dto.Korisnik;

public class UserDAO {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = 
        "SELECT * FROM korisnici WHERE korisnicko_ime=? AND lozinka=?";
    private static final String SQL_IS_USERNAME_USED = 
        "SELECT * FROM korisnici WHERE korisnicko_ime = ?";
    private static final String SQL_INSERT = 
        "INSERT INTO korisnici (uloga, korisnicko_ime, lozinka, ime, prezime, broj_licne_karte, email, broj_telefona) " + 
        "VALUES ('KLIJENT', ?, ?, ?, ?, ?, ?, ?)";

    // Prijava korisnika
    public static Korisnik selectByUsernameAndPassword(String korisnickoIme, String lozinka) {
        Korisnik korisnik = null;
        Connection connection = null;
        ResultSet rs = null;
        Object[] values = {korisnickoIme, lozinka};

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_USERNAME_AND_PASSWORD, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                korisnik = new Korisnik(
                    rs.getInt("id"),
                    rs.getString("uloga"),
                    rs.getString("korisnicko_ime"),
                    rs.getString("lozinka"),
                    rs.getString("ime"),
                    rs.getString("prezime"),
                    rs.getString("broj_licne_karte"),
                    rs.getString("email"),
                    rs.getString("broj_telefona"),
                    rs.getString("avatar"),
                    rs.getBoolean("blokiran")
                );
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return korisnik;
    }

    // Provera da li je korisničko ime već zauzeto
    public static boolean isUserNameUsed(String korisnickoIme) {
        boolean result = false;
        Connection connection = null;
        ResultSet rs = null;
        Object[] values = {korisnickoIme};

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_IS_USERNAME_USED, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = true;
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }

    // Registracija novog korisnika
    public static boolean insert(Korisnik korisnik) {
        boolean result = false;
        Connection connection = null;
        ResultSet generatedKeys = null;
        Object[] values = {
            korisnik.getKorisnickoIme(),
            korisnik.getLozinka(),
            korisnik.getIme(),
            korisnik.getPrezime(),
            korisnik.getBrojLicneKarte(),
            korisnik.getEmail(),
            korisnik.getBrojTelefona()
        };

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                result = true;
            }

            generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                korisnik.setId(generatedKeys.getInt(1));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }
    
    public static boolean updatePassword(Korisnik korisnik) {
        boolean result = false;
        Connection connection = null;
        Object[] values = {korisnik.getLozinka(), korisnik.getId()};

        try {
            connection = connectionPool.checkOut();
            String sql = "UPDATE korisnici SET lozinka=? WHERE id=?";
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, sql, false, values);
            int affectedRows = pstmt.executeUpdate();
            result = affectedRows > 0;
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }
    
    public static boolean deactivateAccount(int userId) {
        boolean result = false;
        Connection connection = null;

        try {
            connection = connectionPool.checkOut();
            String sql = "UPDATE korisnici SET blokiran=TRUE WHERE id=?";
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, sql, false, userId);
            int affectedRows = pstmt.executeUpdate();
            result = affectedRows > 0;
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }


}
