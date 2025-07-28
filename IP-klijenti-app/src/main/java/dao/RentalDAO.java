package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import dto.Iznajmljivanje;

public class RentalDAO {

    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private static final String SQL_GET_RENTALS_BY_USER_ID = 
        "SELECT ps.identifikator, i.datum_pocetka, i.trajanje, i.cijena, i.platna_kartica " +
        "FROM iznajmljivanja i " +
        "JOIN prevozna_sredstva ps ON i.prevozno_sredstvo_id = ps.id " +
        "WHERE i.korisnik_id = ?";

    public static List<Iznajmljivanje> getRentalsByUserId(int korisnikId) {
        List<Iznajmljivanje> iznajmljivanja = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;
        
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_GET_RENTALS_BY_USER_ID, false, korisnikId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Iznajmljivanje iznajmljivanje = new Iznajmljivanje();
                iznajmljivanje.setIdentifikator(rs.getString("identifikator"));

                Timestamp timestamp = rs.getTimestamp("datum_pocetka");
                String datumPocetkaStr = (timestamp != null) ? timestamp.toString() : "N/A";
                iznajmljivanje.setDatumPocetka(datumPocetkaStr);

                iznajmljivanje.setTrajanje(rs.getInt("trajanje"));
                iznajmljivanje.setCijena(rs.getInt("cijena"));
                iznajmljivanje.setPlatnaKartica(rs.getString("platna_kartica"));

                System.out.println("Učitano iz baze: " + iznajmljivanje.toString());  // Dodaj ovo za proveru

                iznajmljivanja.add(iznajmljivanje);
            }

            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return iznajmljivanja;
    }
    
    public static int startRental(String identifikator, int korisnikId, Timestamp datumPocetka, String platnaKartica, String identifikacioniDokument, String vozackaDozvola) {
        Connection connection = null;
        ResultSet generatedKeys = null;
        int iznajmljivanjeId = -1;

        try {
            connection = connectionPool.checkOut();

            // Dohvatanje ID-a i trenutne lokacije prevoznog sredstva
            String getVehicleSql = "SELECT id, trenutna_lokacija FROM prevozna_sredstva WHERE identifikator = ?";
            PreparedStatement getVehicleStmt = DAOUtil.prepareStatement(connection, getVehicleSql, false, identifikator);
            ResultSet rs = getVehicleStmt.executeQuery();
            
            int prevoznoSredstvoId = -1;
            String trenutnaLokacija = null;
            if (rs.next()) {
                prevoznoSredstvoId = rs.getInt("id");
                trenutnaLokacija = rs.getString("trenutna_lokacija");
            }

            if (prevoznoSredstvoId != -1 && trenutnaLokacija != null) {
                String sql = "INSERT INTO iznajmljivanja (prevozno_sredstvo_id, korisnik_id, datum_pocetka, lokacija_preuzimanja, trajanje, platna_kartica, identifikacioni_dokument, vozacka_dozvola, cijena) " +
                             "VALUES (?, ?, ?, ?, 0, ?, ?, ?, 0)";
                PreparedStatement pstmt = DAOUtil.prepareStatement(connection, sql, true,
                    prevoznoSredstvoId, korisnikId, datumPocetka, trenutnaLokacija, platnaKartica, identifikacioniDokument, vozackaDozvola);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        iznajmljivanjeId = generatedKeys.getInt(1);
                    }
                }
                pstmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return iznajmljivanjeId;
    }

    
    public static void finishRental(int iznajmljivanjeId, Timestamp datumZavrsetka, int trajanje, String lokacijaVracanja, int ukupnaCena) {
        Connection connection = null;

        try {
            connection = connectionPool.checkOut();
            String sql = "UPDATE iznajmljivanja SET datum_zavrsetka = ?, trajanje = ?, lokacija_vracanja = ?, cijena = ? WHERE id = ?";
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, sql, false, datumZavrsetka, trajanje, lokacijaVracanja, ukupnaCena, iznajmljivanjeId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
    }
    
    public static Iznajmljivanje getRentalById(int iznajmljivanjeId) {
        Iznajmljivanje iznajmljivanje = null;
        Connection connection = null;
        ResultSet rs = null;

        String sql = "SELECT ps.identifikator, i.datum_pocetka, i.datum_zavrsetka, i.trajanje, i.cijena, i.platna_kartica " +
                     "FROM iznajmljivanja i " +
                     "JOIN prevozna_sredstva ps ON i.prevozno_sredstvo_id = ps.id " +
                     "WHERE i.id = ?";

        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, sql, false, iznajmljivanjeId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                iznajmljivanje = new Iznajmljivanje();
                iznajmljivanje.setIdentifikator(rs.getString("identifikator"));
                iznajmljivanje.setDatumPocetka(rs.getTimestamp("datum_pocetka").toString());
                iznajmljivanje.setDatumZavrsetka(rs.getTimestamp("datum_zavrsetka").toString());  // Ovo je nova linija
                iznajmljivanje.setTrajanje(rs.getInt("trajanje"));
                iznajmljivanje.setCijena(rs.getInt("cijena"));
                iznajmljivanje.setPlatnaKartica(rs.getString("platna_kartica"));
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }

        return iznajmljivanje;
    }



}
