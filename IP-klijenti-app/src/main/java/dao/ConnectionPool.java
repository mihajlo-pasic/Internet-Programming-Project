package dao;

import java.sql.*;
import java.util.*;

public class ConnectionPool {

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    private static ConnectionPool connectionPool;

    static {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("dao.ConnectionPool");
        String jdbcURL = bundle.getString("jdbcURL");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        String driver = bundle.getString("driver");
        int preconnectCount = 0;
        int maxIdleConnections = 10;
        int maxConnections = 10;
        try {
            Class.forName(driver);
            preconnectCount = Integer.parseInt(bundle.getString("preconnectCount"));
            maxIdleConnections = Integer.parseInt(bundle.getString("maxIdleConnections"));
            maxConnections = Integer.parseInt(bundle.getString("maxConnections"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            connectionPool = new ConnectionPool(jdbcURL, username, password, preconnectCount, maxIdleConnections, maxConnections);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected ConnectionPool(String jdbcURL, String username, String password, int preconnectCount, int maxIdleConnections, int maxConnections) throws SQLException {
        freeConnections = new Vector<>();
        usedConnections = new Vector<>();
        this.jdbcURL = jdbcURL;
        this.username = username;
        this.password = password;
        this.preconnectCount = preconnectCount;
        this.maxIdleConnections = maxIdleConnections;
        this.maxConnections = maxConnections;

        for (int i = 0; i < preconnectCount; i++) {
            Connection conn = DriverManager.getConnection(jdbcURL, username, password);
            conn.setAutoCommit(true);
            freeConnections.addElement(conn);
        }
        connectCount = preconnectCount;
    }

    public synchronized Connection checkOut() throws SQLException {
        Connection conn = null;
        if (freeConnections.size() > 0) {
            conn = freeConnections.remove(0);
            usedConnections.addElement(conn);
        } else if (connectCount < maxConnections) {
            conn = DriverManager.getConnection(jdbcURL, username, password);
            usedConnections.addElement(conn);
            connectCount++;
        } else {
            try {
                wait();
                conn = freeConnections.remove(0);
                usedConnections.addElement(conn);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return conn;
    }

    public synchronized void checkIn(Connection conn) {
        if (conn == null) return;
        if (usedConnections.remove(conn)) {
            freeConnections.addElement(conn);
            while (freeConnections.size() > maxIdleConnections) {
                int lastOne = freeConnections.size() - 1;
                try {
                    freeConnections.remove(lastOne).close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            notify();
        }
    }

    private String jdbcURL;
    private String username;
    private String password;
    private int preconnectCount;
    private int connectCount;
    private int maxIdleConnections;
    private int maxConnections;
    private Vector<Connection> usedConnections;
    private Vector<Connection> freeConnections;
}