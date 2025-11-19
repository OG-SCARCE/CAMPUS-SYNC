package com.campussync.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection
 *
 * Centralized utility class for creating JDBC connections to the MySQL
 * database used by the CampusSync application.
 *
 * Responsibilities:
 *  - Load MySQL JDBC driver once (static block)
 *  - Provide getConnection() for DAOs and Servlets
 *  - Maintain clean, reusable, isolated database configuration
 *
 * Notes:
 *  - Credentials stored as constants for prototype simplicity.
 *  - In production → move credentials to environment variables, JNDI, or config files.
 *  - For scalability → use Connection Pooling (HikariCP, Apache DBCP).
 */
public class DBConnection {

    // ---------------------------------------------------------------------
    // Database Configuration (Update these fields if DB credentials change)
    // ---------------------------------------------------------------------
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DBNAME = "campussync";
    private static final String USER = "root";
    private static final String PASS = "1234";

    // JDBC connection URL
    // serverTimezone avoids timezone-related warnings in MySQL Connector/J
    private static final String URL = String.format(
            "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            HOST, PORT, DBNAME
    );

    // ---------------------------------------------------------------------
    // Static Initialization Block
    // Loads MySQL Driver ONCE when class is loaded.
    // ---------------------------------------------------------------------
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL 8+ driver
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: MySQL JDBC Driver not found. " +
                    "Ensure mysql-connector-j.jar is in the classpath.");
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------
    // Get Connection
    //
    // @return A fresh JDBC Connection object
    // @throws SQLException If connection cannot be established
    //
    // Caller (DAO/Servlet) MUST close the connection after use.
    // ---------------------------------------------------------------------
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ---------------------------------------------------------------------
    // Optional cleanup helper
    // Quietly closes any AutoCloseable (Connection, Statement, ResultSet)
    // without forcing callers to handle exceptions during cleanup.
    // ---------------------------------------------------------------------
    public static void closeQuietly(AutoCloseable ac) {
        if (ac == null) return;
        try {
            ac.close();
        } catch (Exception ignored) { }
    }
}
