package electricity.billing.system;

public class TaxDAO {
    // Singleton instance
    private static TaxDAO instance;

    // Private constructor to prevent instantiation
    private TaxDAO() {}

    // Thread-safe singleton access
    public static synchronized TaxDAO getInstance() {
        if (instance == null) {
            instance = new TaxDAO();
        }
        return instance;
    }

    // Return the Tax class (constants only, no need to instantiate)
    public Tax getTax() {
        return new Tax(); // simplest
    }

}
