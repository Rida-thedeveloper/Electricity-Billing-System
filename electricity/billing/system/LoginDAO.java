package electricity.billing.system;

import java.sql.*;

public class LoginDAO {

    private static LoginDAO instance;
    private Conn c;

    private LoginDAO() {
        c = Conn.getInstance(); // Reuse single DB connection
    }

    public static synchronized LoginDAO getInstance() {
        if (instance == null) {
            instance = new LoginDAO();
        }
        return instance;
    }

    // Authenticate user
    public String authenticate(String username, String password, String userType) {
        String query = "SELECT meter_no FROM login WHERE username = ? AND password = ? AND user = ?";
        try {
            PreparedStatement pst = c.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, userType);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("meter_no");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Invalid login
    }

    // Create new Admin account
    public void createAdmin(String username, String name, String password) throws SQLException {
        String query = "INSERT INTO login (meter_no, username, name, password, user) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = c.getConnection().prepareStatement(query);
        pst.setString(1, "ADMIN"); // Admin doesn't need meter number
        pst.setString(2, username);
        pst.setString(3, name);
        pst.setString(4, password);
        pst.setString(5, "Admin");
        pst.executeUpdate();
    }

    // Update existing Customer account
    public void updateCustomerAccount(String meter, String username, String password) throws SQLException {
        String query = "UPDATE login SET username = ?, password = ?, user = ? WHERE meter_no = ?";
        PreparedStatement pst = c.getConnection().prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        pst.setString(3, "Customer");
        pst.setString(4, meter);
        pst.executeUpdate();
    }
    // Check if a username already exists (for both Admin & Customer)
    public boolean isUsernameExists(String username) {
        String query = "SELECT username FROM login WHERE username = ?";
        try {
            PreparedStatement pst = c.getConnection().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // true means username already in database
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


