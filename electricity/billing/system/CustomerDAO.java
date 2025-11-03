package electricity.billing.system;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {

    private static CustomerDAO instance;
    private Conn conn;

    private CustomerDAO() {
        conn = Conn.getInstance();
    }

    public static synchronized CustomerDAO getInstance() {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        return instance;
    }

    // Add new customer + login entry
    public void addCustomer(String name, String meter, String address, String city, String state, String email, String phone) throws SQLException {
        // Insert into customer table
        String query1 = "INSERT INTO customer (name, meter_no, address, city, state, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps1 = conn.getConnection().prepareStatement(query1);
        ps1.setString(1, name);
        ps1.setString(2, meter);
        ps1.setString(3, address);
        ps1.setString(4, city);
        ps1.setString(5, state);
        ps1.setString(6, email);
        ps1.setString(7, phone);
        ps1.executeUpdate();
        ps1.close();

        // Insert into login table
        String query2 = "INSERT INTO login (meter_no, username, name, password, user) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps2 = conn.getConnection().prepareStatement(query2);
        ps2.setString(1, meter);
        ps2.setString(2, "");             // username blank
        ps2.setString(3, name);
        ps2.setString(4, "");             // password blank
        ps2.setString(5, "Customer");     // user type
        ps2.executeUpdate();
        ps2.close();
    }

    // Get all meter numbers
    public ArrayList<String> getAllMeterNumbers() {
        ArrayList<String> meters = new ArrayList<>();
        try {
            String query = "SELECT meter_no FROM customer";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                meters.add(rs.getString("meter_no"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meters;
    }

    // Get a specific customer by meter number
    public Customer getCustomerByMeter(String meterNo) {
        Customer customer = null;
        try {
            String query = "SELECT * FROM customer WHERE meter_no=?";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, meterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                        rs.getString("name"),
                        rs.getString("meter_no"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    //  Update customer info (needed for UpdateInformation class)
    public void updateCustomerInfo(String meter, String address, String city, String state, String email, String phone) {
        try {
            String query = "UPDATE customer SET address=?, city=?, state=?, email=?, phone=? WHERE meter_no=?";
            PreparedStatement ps = conn.getConnection().prepareStatement(query);
            ps.setString(1, address);
            ps.setString(2, city);
            ps.setString(3, state);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, meter);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get ALL customers as ResultSet (for JTable in CustomerDetails)
    public ResultSet getAllCustomersResultSet() {
        try {
            String query = "SELECT * FROM customer";
            return conn.getConnection().createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
