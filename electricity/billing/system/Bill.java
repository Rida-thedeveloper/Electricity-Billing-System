package electricity.billing.system;

// Simple Bill model class
public class Bill {
    private String meterNo;
    private String month;
    private int units;
    private int totalBill;
    private String status;

    public Bill(String meterNo, String month, int units, int totalBill, String status) {
        this.meterNo = meterNo;
        this.month = month;
        this.units = units;
        this.totalBill = totalBill;
        this.status = status;
    }

    // Getters
    public String getMeterNo() { return meterNo; }
    public String getMonth() { return month; }
    public int getUnits() { return units; }
    public int getTotalBill() { return totalBill; }
    public String getStatus() { return status; }

    // Setters if needed
    public void setStatus(String status) { this.status = status; }
}
