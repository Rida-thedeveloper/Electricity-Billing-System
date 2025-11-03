package electricity.billing.system;

import java.sql.*;

// Singleton database connection class
public class Conn {

    private static Conn instance;       // Singleton instance
    private Connection connection;      // Encapsulated Connection

    // Private constructor prevents external instantiation
    private Conn() {
        try {
            // JDBC URL, username, password
            connection = DriverManager.getConnection("jdbc:mysql:///ebs", "root", "ridaanum2");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Thread-safe method to get singleton instance
    public static synchronized Conn getInstance() {
        if (instance == null) {
            instance = new Conn();
        }
        return instance;
    }

    // Getter for connection (used by DAOs)
    public Connection getConnection() {
        return connection;
    }

    // Optional helper to create statements safely
    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    // Optional helper to close connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            instance = null; // Allow re-initialization if needed
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
