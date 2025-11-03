package electricity.billing.system;

import java.util.Random;

public class CustomerService {

    private static CustomerService instance;
    private CustomerDAO customerDAO;

    private CustomerService() {
        customerDAO = CustomerDAO.getInstance();
    }

    public static synchronized CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    // Generate a new meter number
    public String generateMeterNumber() {
        Random ran = new Random();
        long number = ran.nextLong() % 1000000;
        return String.valueOf(Math.abs(number));
    }

    // Add a new customer
    public void addCustomer(String name, String meter, String address, String city,
                            String state, String email, String phone) throws Exception {
        customerDAO.addCustomer(name, meter, address, city, state, email, phone);
    }
}
