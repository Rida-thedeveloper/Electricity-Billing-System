package electricity.billing.system;

import java.sql.*;

public class MeterInfoDAO {
    private static MeterInfoDAO instance;
    private Conn conn;

    private MeterInfoDAO() { conn = Conn.getInstance(); }

    public static MeterInfoDAO getInstance() {
        if(instance == null) instance = new MeterInfoDAO();
        return instance;
    }

    public void insertMeterInfo(String meter, String location, String type, String code,
                                String typebill, String days) throws SQLException {
        String query = "INSERT INTO meter_info VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.getConnection().prepareStatement(query);
        ps.setString(1, meter);
        ps.setString(2, location);
        ps.setString(3, type);
        ps.setString(4, code);
        ps.setString(5, typebill);
        ps.setInt(6, Integer.parseInt(days));
        ps.executeUpdate();
        ps.close();
    }

    public MeterInfoData getMeterInfoByMeter(String meterNo) {
        // Dummy data for now
        return new MeterInfoData(meterNo, "Outside", "Electric Meter", "011", "Normal", 30);
    }
}
