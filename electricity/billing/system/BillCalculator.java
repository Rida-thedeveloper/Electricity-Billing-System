package electricity.billing.system;

import java.sql.*;

// Handles bill calculation (Before: logic inside UI)
public class BillCalculator {

    public static int calculateTotalBill(int unitsConsumed) {
        int totalBill = 0;
        try {
            String query = "SELECT * FROM tax";
            ResultSet rs = Conn.getInstance().createStatement().executeQuery(query);
            while (rs.next()) {
                totalBill += unitsConsumed * rs.getInt("cost_per_unit");
                totalBill += rs.getInt("meter_rent");
                totalBill += rs.getInt("service_charge");
                totalBill += rs.getInt("service_tax");
                totalBill += rs.getInt("K_electric_tax");
                totalBill += rs.getInt("fixed_tax");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalBill;
    }
}

