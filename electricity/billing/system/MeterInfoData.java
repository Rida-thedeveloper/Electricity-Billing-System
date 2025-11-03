package electricity.billing.system;

public class MeterInfoData {
    private String meterNo;
    private String meterLocation;
    private String meterType;
    private String phaseCode;
    private String billType;
    private int days;

    public MeterInfoData(String meterNo, String meterLocation, String meterType,
                         String phaseCode, String billType, int days) {
        this.meterNo = meterNo;
        this.meterLocation = meterLocation;
        this.meterType = meterType;
        this.phaseCode = phaseCode;
        this.billType = billType;
        this.days = days;
    }

    public String getMeterNo() { return meterNo; }
    public String getMeterLocation() { return meterLocation; }
    public String getMeterType() { return meterType; }
    public String getPhaseCode() { return phaseCode; }
    public String getBillType() { return billType; }
    public int getDays() { return days; }
}
