package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Before: UI + DB + Business logic all together
// After: UI only handles display & user interaction
public class CalculateBill extends JFrame implements ActionListener {

    private JTextField tfunits;
    private JButton next, cancel;
    private JLabel lblname, lbladdress;
    private Choice meternumber, cmonth;

    public CalculateBill() {
        setSize(800, 500);
        setLocation(400, 150);

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);

        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setBounds(100, 10, 400, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);

        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(100, 80, 100, 20);
        p.add(lblmeternumber);

        meternumber = new Choice();
        populateMeterNumbers(); // Before: SQL in constructor, After: DAO handles DB
        meternumber.setBounds(240, 80, 200, 20);
        p.add(meternumber);

        JLabel lblnameLabel = new JLabel("Name");
        lblnameLabel.setBounds(100, 120, 100, 20);
        p.add(lblnameLabel);

        lblname = new JLabel("");
        lblname.setBounds(240, 120, 200, 20);
        p.add(lblname);

        JLabel lbladdressLabel = new JLabel("Address");
        lbladdressLabel.setBounds(100, 160, 100, 20);
        p.add(lbladdressLabel);

        lbladdress = new JLabel();
        lbladdress.setBounds(240, 160, 200, 20);
        p.add(lbladdress);

        updateCustomerDetails(meternumber.getSelectedItem());

        meternumber.addItemListener(ie -> updateCustomerDetails(meternumber.getSelectedItem()));

        JLabel lblunits = new JLabel("Units Consumed");
        lblunits.setBounds(100, 200, 100, 20);
        p.add(lblunits);

        tfunits = new JTextField();
        tfunits.setBounds(240, 200, 200, 20);
        p.add(tfunits);

        JLabel lblmonth = new JLabel("Month");
        lblmonth.setBounds(100, 240, 100, 20);
        p.add(lblmonth);

        cmonth = new Choice();
        String[] months = {"January","February","March","April","May","June",
                "July","August","September","October","November","December"};
        for(String m : months) cmonth.add(m);
        cmonth.setBounds(240, 240, 200, 20);
        p.add(cmonth);

        next = new JButton("Submit");
        next.setBounds(120, 350, 100, 25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);

        cancel = new JButton("Cancel");
        cancel.setBounds(250, 350, 100, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        p.add(cancel);

        setLayout(new BorderLayout());
        add(p, "Center");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/calculate bill.jpg"));
        Image i2 = i1.getImage().getScaledInstance(270, 350, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    // Before: DB query in constructor multiple times
    // After: DAO used to fetch meter numbers
    private void populateMeterNumbers() {
        try {
            for(String meter : CustomerDAO.getInstance().getAllMeterNumbers()) {
                meternumber.add(meter);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Before: Duplicate SQL in item listener and constructor
    // After: Single method using DAO
    private void updateCustomerDetails(String meter) {
        try {
            Customer customer = CustomerDAO.getInstance().getCustomerByMeter(meter);
            if(customer != null) {
                lblname.setText(customer.getName());
                lbladdress.setText(customer.getAddress());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            String meter = meternumber.getSelectedItem();
            String unitText = tfunits.getText().trim();
            String month = cmonth.getSelectedItem();

            // 🛑 Validation 1: Empty input check
            if (unitText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter the number of units.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return; // stop further execution
            }

            int units;
            try {
                // ✅ Validation 2: Check if number is valid
                units = Integer.parseInt(unitText);
                if (units < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Units cannot be negative.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for units.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ Only runs if input is valid
            int totalBill = BillCalculator.calculateTotalBill(units);

            try {
                BillDAO.getInstance().insertBill(meter, month, units, totalBill);
                JOptionPane.showMessageDialog(null, "Customer Bill Updated Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new CalculateBill();
    }
}
