package electricity.billing.system;

public class Tax {
    public static final double COST_PER_UNIT = 10.0;
    public static final double METER_RENT = 5.0;
    public static final double SERVICE_CHARGE = 2.0;
    public static final double SERVICE_TAX = 1.5;
    public static final double K_electric_tax = 0.5;
    public static final double FIXED_TAX = 3.0;

    public Tax() {} // still private

    // Add getters
    public double getCostPerUnit() { return COST_PER_UNIT; }
    public double getMeterRent() { return METER_RENT; }
    public double getServiceCharge() { return SERVICE_CHARGE; }
    public double getServiceTax() { return SERVICE_TAX; }
    public double getKelectricTax() { return K_electric_tax; }
    public double getFixedTax() { return FIXED_TAX; }
}
