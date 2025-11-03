package electricity.billing.system;

import java.sql.*;

public class BillDAO {

    private static BillDAO instance;
    private Conn c;

    private BillDAO() {
        c = Conn.getInstance();
    }

    public static synchronized BillDAO getInstance() {
        if (instance == null) {
            instance = new BillDAO();
        }
        return instance;
    }

    // Insert a new bill
    public void insertBill(String meter, String month, int units, int totalBill) throws SQLException {
        String query = "INSERT INTO bill (meter_no, month, units, totalbill, status) VALUES (?, ?, ?, ?, 'Not Paid')";
        PreparedStatement pst = c.getConnection().prepareStatement(query);
        pst.setString(1, meter);
        pst.setString(2, month);
        pst.setInt(3, units);
        pst.setInt(4, totalBill);
        pst.executeUpdate();
        pst.close();
    }

    // Get all bills
    public ResultSet getAllBills() throws SQLException {
        String query = "SELECT * FROM bill";
        return c.getConnection().createStatement().executeQuery(query);
    }

    // Get bills by meter number
    public ResultSet getBillByMeter(String meter) throws SQLException {
        if (meter == null || meter.isEmpty()) {
            String query = "SELECT * FROM bill";
            return c.getConnection().createStatement().executeQuery(query);
        } else {
            String query = "SELECT * FROM bill WHERE meter_no=?";
            PreparedStatement pst = c.getConnection().prepareStatement(query);
            pst.setString(1, meter);
            return pst.executeQuery();
        }
    }

    // Single bill by meter & month
    public Bill getBillByMeterAndMonth(String meter, String month) throws SQLException {
        String query = "SELECT * FROM bill WHERE meter_no=? AND month=?";
        PreparedStatement pst = c.getConnection().prepareStatement(query);
        pst.setString(1, meter);
        pst.setString(2, month);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new Bill(
                    rs.getString("meter_no"),
                    rs.getString("month"),
                    rs.getInt("units"),
                    rs.getInt("totalbill"),
                    rs.getString("status")
            );
        }
        return null;
    }

    // Pay a bill
    public void payBill(String meter, String month) throws SQLException {
        String query = "UPDATE bill SET status='Paid' WHERE meter_no=? AND month=?";
        PreparedStatement pst = c.getConnection().prepareStatement(query);
        pst.setString(1, meter);
        pst.setString(2, month);
        pst.executeUpdate();
        pst.close();
    }
}
