package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// NewCustomer: only handles UI now
public class NewCustomer extends JFrame implements ActionListener {

    private JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
    private JButton next, cancel;
    private JLabel lblmeter;

    public NewCustomer() {
        setSize(700, 500);
        setLocation(400, 200);

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);

        JLabel heading = new JLabel("New Customer");
        heading.setBounds(180, 10, 200, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);

        // Customer Name
        JLabel lblname = new JLabel("Customer Name");
        lblname.setBounds(100, 80, 100, 20);
        p.add(lblname);

        tfname = new JTextField();
        tfname.setBounds(240, 80, 200, 20);
        p.add(tfname);

        // Meter Number
        JLabel lblmeterno = new JLabel("Meter Number");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);

        lblmeter = new JLabel(CustomerService.getInstance().generateMeterNumber());
        lblmeter.setBounds(240, 120, 100, 20);
        p.add(lblmeter);

        // Address
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(240, 160, 200, 20);
        p.add(tfaddress);

        // City
        JLabel lblcity = new JLabel("City");
        lblcity.setBounds(100, 200, 100, 20);
        p.add(lblcity);

        tfcity = new JTextField();
        tfcity.setBounds(240, 200, 200, 20);
        p.add(tfcity);

        // State
        JLabel lblstate = new JLabel("State");
        lblstate.setBounds(100, 240, 100, 20);
        p.add(lblstate);

        tfstate = new JTextField();
        tfstate.setBounds(240, 240, 200, 20);
        p.add(tfstate);

        // Email
        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(100, 280, 100, 20);
        p.add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(240, 280, 200, 20);
        p.add(tfemail);

        // Phone
        JLabel lblphone = new JLabel("Phone Number");
        lblphone.setBounds(100, 320, 100, 20);
        p.add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(240, 320, 200, 20);
        p.add(tfphone);

        // Buttons
        next = new JButton("Next");
        next.setBounds(120, 390, 100,25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);

        cancel = new JButton("Cancel");
        cancel.setBounds(250, 390, 100,25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        p.add(cancel);

        setLayout(new BorderLayout());
        add(p, "Center");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/new.jpg"));
        Image i2 = i1.getImage().getScaledInstance(230, 300, Image.SCALE_DEFAULT);
        add(new JLabel(new ImageIcon(i2)), "West");

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            String name = tfname.getText().trim();
            String meter = lblmeter.getText().trim();
            String address = tfaddress.getText().trim();
            String city = tfcity.getText().trim();
            String state = tfstate.getText().trim();
            String email = tfemail.getText().trim();
            String phone = tfphone.getText().trim();

            // ✅ Validation
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Customer Name cannot be empty");
                return;
            }
            if (address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Address cannot be empty");
                return;
            }
            if (city.isEmpty()) {
                JOptionPane.showMessageDialog(null, "City cannot be empty");
                return;
            }
            if (state.isEmpty()) {
                JOptionPane.showMessageDialog(null, "State cannot be empty");
                return;
            }
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Email cannot be empty");
                return;
            }
            if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
                JOptionPane.showMessageDialog(null, "Enter a valid email address");
                return;
            }
            if (phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phone number cannot be empty");
                return;
            }
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Enter a valid 10-digit phone number");
                return;
            }

            try {
                // Delegate to Service layer
                CustomerService.getInstance().addCustomer(name, meter, address, city, state, email, phone);

                JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
                setVisible(false);

                // Proceed to meter info
                new MeterInfo(meter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new NewCustomer();
    }
}