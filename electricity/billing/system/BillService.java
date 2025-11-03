package electricity.billing.system;

import java.sql.SQLException;
import java.util.*;

public class BillService {

    private static BillService instance;
    private CustomerDAO customerDAO;
    private BillDAO billDAO;

    private BillService() {
        customerDAO = CustomerDAO.getInstance();
        billDAO = BillDAO.getInstance();
    }

    public static synchronized BillService getInstance() {
        if (instance == null) {
            instance = new BillService();
        }
        return instance;
    }

    // Get customer by meter
    public Customer getCustomer(String meter) {
        return customerDAO.getCustomerByMeter(meter);
    }

    // Get bill by meter and month
    public Bill getBill(String meter, String month) throws SQLException {
        return billDAO.getBillByMeterAndMonth(meter, month);
    }

    // Pay bill
    public void payBill(String meter, String month) throws Exception {
        billDAO.payBill(meter, month);
    }
}
