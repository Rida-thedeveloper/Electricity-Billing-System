package electricity.billing.system;

import javax.swing.*;
import java.awt.*;

public class ViewInformation extends JFrame {

    private JLabel nameLabel, meterLabel, addressLabel, cityLabel, stateLabel, emailLabel, phoneLabel;
    private JButton cancel;

    public ViewInformation(String meter) {
        setTitle("View Customer Information");
        setBounds(300, 100, 900, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 900, 500);
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setLayout(null);
        add(mainPanel);

        // Heading
        JLabel heading = new JLabel("VIEW CUSTOMER INFORMATION");
        heading.setBounds(200, 20, 500, 40);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(0, 102, 204));
        mainPanel.add(heading);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(30, 80, 820, 250);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
        mainPanel.add(formPanel);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font valueFont = new Font("Segoe UI", Font.BOLD, 16);

        // Left column
        nameLabel = addLabelPair(formPanel, "Name:", 20, 20, labelFont, valueFont);
        meterLabel = addLabelPair(formPanel, "Meter Number:", 20, 60, labelFont, valueFont);
        addressLabel = addLabelPair(formPanel, "Address:", 20, 100, labelFont, valueFont);
        cityLabel = addLabelPair(formPanel, "City:", 20, 140, labelFont, valueFont);

        // Right column
        stateLabel = addLabelPair(formPanel, "State:", 400, 20, labelFont, valueFont);
        emailLabel = addLabelPair(formPanel, "Email:", 400, 60, labelFont, valueFont);
        phoneLabel = addLabelPair(formPanel, "Phone:", 400, 100, labelFont, valueFont);

        // Load customer data safely
        loadCustomerData(meter);

        // Cancel button
        cancel = new JButton("Cancel");
        cancel.setBounds(360, 180, 120, 35);
        cancel.setBackground(new Color(204, 0, 0));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancel.setFocusPainted(false);
        cancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancel.addActionListener(e -> setVisible(false));
        formPanel.add(cancel);

        setVisible(true);
    }

    private JLabel addLabelPair(JPanel panel, String labelText, int x, int y, Font labelFont, Font valueFont) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 120, 25);
        label.setFont(labelFont);
        panel.add(label);

        JLabel value = new JLabel("");
        value.setBounds(x + 140, y, 250, 25);
        value.setFont(valueFont);
        value.setForeground(new Color(51, 51, 51));
        panel.add(value);

        return value;
    }

    private void loadCustomerData(String meter) {
        try {
            Customer customer = CustomerDAO.getInstance().getCustomerByMeter(meter);
            if (customer != null) {
                nameLabel.setText(customer.getName());
                meterLabel.setText(customer.getMeter());
                addressLabel.setText(customer.getAddress());
                cityLabel.setText(customer.getCity());
                stateLabel.setText(customer.getState());
                emailLabel.setText(customer.getEmail());
                phoneLabel.setText(customer.getPhone());
            } else {
                JOptionPane.showMessageDialog(null, "Customer not found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading customer data.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewInformation("");
    }
}
