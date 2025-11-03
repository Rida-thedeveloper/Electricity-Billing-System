package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateInformation extends JFrame implements ActionListener {

    private JTextField tfAddress, tfCity, tfState, tfEmail, tfPhone;
    private JButton update, cancel;
    private String meter;
    private JLabel lblName, lblMeter;

    public UpdateInformation(String meter) {
        this.meter = meter;
        setBounds(300, 150, 1050, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1050, 500);
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setLayout(null);
        add(mainPanel);

        // Heading
        JLabel heading = new JLabel("UPDATE CUSTOMER INFORMATION");
        heading.setBounds(50, 20, 500, 40);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(0, 102, 204));
        mainPanel.add(heading);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(30, 80, 480, 370);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
        mainPanel.add(formPanel);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 120, 25);
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel);

        lblName = new JLabel("");
        lblName.setBounds(180, 20, 250, 25);
        lblName.setFont(fieldFont);
        lblName.setForeground(new Color(51, 51, 51));
        formPanel.add(lblName);

        // Meter Number
        JLabel meterLabel = new JLabel("Meter Number:");
        meterLabel.setBounds(20, 60, 120, 25);
        meterLabel.setFont(labelFont);
        formPanel.add(meterLabel);

        lblMeter = new JLabel("");
        lblMeter.setBounds(180, 60, 250, 25);
        lblMeter.setFont(fieldFont);
        lblMeter.setForeground(new Color(51, 51, 51));
        formPanel.add(lblMeter);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(20, 100, 120, 25);
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel);

        tfAddress = new JTextField();
        styleTextField(tfAddress);
        tfAddress.setBounds(180, 100, 250, 30);
        formPanel.add(tfAddress);

        // City
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(20, 140, 120, 25);
        cityLabel.setFont(labelFont);
        formPanel.add(cityLabel);

        tfCity = new JTextField();
        styleTextField(tfCity);
        tfCity.setBounds(180, 140, 250, 30);
        formPanel.add(tfCity);

        // State
        JLabel stateLabel = new JLabel("State:");
        stateLabel.setBounds(20, 180, 120, 25);
        stateLabel.setFont(labelFont);
        formPanel.add(stateLabel);

        tfState = new JTextField();
        styleTextField(tfState);
        tfState.setBounds(180, 180, 250, 30);
        formPanel.add(tfState);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 220, 120, 25);
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel);

        tfEmail = new JTextField();
        styleTextField(tfEmail);
        tfEmail.setBounds(180, 220, 250, 30);
        formPanel.add(tfEmail);

        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(20, 260, 120, 25);
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel);

        tfPhone = new JTextField();
        styleTextField(tfPhone);
        tfPhone.setBounds(180, 260, 250, 30);
        formPanel.add(tfPhone);

        // Buttons
        update = createButton("Update", new Color(0, 153, 76));
        update.setBounds(70, 320, 120, 35);
        update.addActionListener(this);
        formPanel.add(update);

        cancel = createButton("Cancel", new Color(204, 0, 0));
        cancel.setBounds(250, 320, 120, 35);
        cancel.addActionListener(this);
        formPanel.add(cancel);

        // Right side image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/updatef.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 370, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(550, 80, 400, 370);
        mainPanel.add(image);

        // Load customer info
        loadCustomerData();

        setVisible(true);
    }

    // Rounded text fields
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    // Rounded buttons
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 1, true));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadCustomerData() {
        try {
            Customer customer = CustomerDAO.getInstance().getCustomerByMeter(meter);
            if (customer != null) {
                lblName.setText(customer.getName());
                lblMeter.setText(customer.getMeter());
                tfAddress.setText(customer.getAddress());
                tfCity.setText(customer.getCity());
                tfState.setText(customer.getState());
                tfEmail.setText(customer.getEmail());
                tfPhone.setText(customer.getPhone());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading customer data");
            e.printStackTrace();
        }
    }

    private void updateCustomerData() {
        try {
            CustomerDAO.getInstance().updateCustomerInfo(
                    meter,
                    tfAddress.getText(),
                    tfCity.getText(),
                    tfState.getText(),
                    tfEmail.getText(),
                    tfPhone.getText()
            );
            JOptionPane.showMessageDialog(null, "User Information Updated Successfully");
            setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating information");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            updateCustomerData();
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateInformation("");
    }
}
