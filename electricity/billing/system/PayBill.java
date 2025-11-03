package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PayBill extends JFrame implements ActionListener {

    private JComboBox<String> cmonth;
    private JButton pay, back;
    private String meter;

    private JLabel labelUnits, labelTotalBill, labelStatus, labelName, labelMeter;

    public PayBill(String meter) {
        this.meter = meter;

        setTitle("Pay Electricity Bill");
        setLayout(null);
        setBounds(280, 100, 950, 650);

        // Background panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBounds(0, 0, 950, 650);
        add(mainPanel);

        // Header
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(0, 102, 204));
        headingPanel.setBounds(0, 0, 950, 70);
        headingPanel.setLayout(null);
        mainPanel.add(headingPanel);

        JLabel heading = new JLabel("Electricity Bill Payment Portal");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);
        heading.setBounds(260, 15, 500, 40);
        headingPanel.add(heading);

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBounds(80, 100, 400, 420);
        infoPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2, true));
        mainPanel.add(infoPanel);

        // Meter number
        JLabel lblMeter = new JLabel("Meter Number:");
        lblMeter.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMeter.setBounds(40, 40, 200, 25);
        infoPanel.add(lblMeter);

        labelMeter = new JLabel("");
        labelMeter.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelMeter.setForeground(new Color(51, 51, 51));
        labelMeter.setBounds(200, 40, 200, 25);
        infoPanel.add(labelMeter);

        // Name
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblName.setBounds(40, 90, 200, 25);
        infoPanel.add(lblName);

        labelName = new JLabel("");
        labelName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelName.setForeground(new Color(51, 51, 51));
        labelName.setBounds(200, 90, 200, 25);
        infoPanel.add(labelName);

        // Month selection
        JLabel lblMonth = new JLabel("Select Month:");
        lblMonth.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMonth.setBounds(40, 140, 200, 25);
        infoPanel.add(lblMonth);

        String[] months = {"January","February","March","April","May","June","July",
                "August","September","October","November","December"};

        cmonth = new JComboBox<>(months);
        cmonth.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmonth.setBounds(200, 140, 160, 30);
        cmonth.setBackground(Color.WHITE);
        cmonth.setFocusable(false);
        cmonth.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        infoPanel.add(cmonth);

        // Units
        JLabel lblUnits = new JLabel("Units:");
        lblUnits.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUnits.setBounds(40, 190, 200, 25);
        infoPanel.add(lblUnits);

        labelUnits = new JLabel("");
        labelUnits.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelUnits.setForeground(new Color(51, 51, 51));
        labelUnits.setBounds(200, 190, 200, 25);
        infoPanel.add(labelUnits);

        // Total Bill
        JLabel lblTotal = new JLabel("Total Bill (PKR):");
        lblTotal.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTotal.setBounds(40, 240, 200, 25);
        infoPanel.add(lblTotal);

        labelTotalBill = new JLabel("");
        labelTotalBill.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelTotalBill.setForeground(new Color(0, 102, 0));
        labelTotalBill.setBounds(200, 240, 200, 25);
        infoPanel.add(labelTotalBill);

        // Status
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblStatus.setBounds(40, 290, 200, 25);
        infoPanel.add(lblStatus);

        labelStatus = new JLabel("");
        labelStatus.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelStatus.setForeground(new Color(204, 0, 0));
        labelStatus.setBounds(200, 290, 200, 25);
        infoPanel.add(labelStatus);

        // Buttons
        pay = new JButton("Pay Now");
        pay.setFont(new Font("Segoe UI", Font.BOLD, 15));
        pay.setBackground(new Color(0, 153, 76));
        pay.setForeground(Color.WHITE);
        pay.setFocusPainted(false);
        pay.setBounds(60, 350, 120, 35);
        pay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pay.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 51), 2, true));
        pay.addActionListener(this);
        infoPanel.add(pay);

        JButton close = new JButton("Close");
        close.setFont(new Font("Segoe UI", Font.BOLD, 15));
        close.setBackground(new Color(102, 102, 102));
        close.setForeground(Color.WHITE);
        close.setFocusPainted(false);
        close.setBounds(220, 350, 120, 35);
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.setBorder(BorderFactory.createLineBorder(new Color(51, 51, 51), 2, true));
        close.addActionListener(e -> dispose()); // closes the current window
        infoPanel.add(close);


        // Bill image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bill.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(520, 150, 400, 300);
        mainPanel.add(image);

        // Load initial data
        loadBillData("January");

        // Listener for month change
        cmonth.addActionListener(e -> loadBillData((String) cmonth.getSelectedItem()));

        setVisible(true);
    }

    // Load bill and customer data using Service layer
    private void loadBillData(String month) {
        try {
            Customer customer = BillService.getInstance().getCustomer(meter);
            if (customer != null) labelName.setText(customer.getName());
            labelMeter.setText(meter);

            Bill bill = BillService.getInstance().getBill(meter, month);
            if (bill != null) {
                labelUnits.setText(String.valueOf(bill.getUnits()));
                labelTotalBill.setText(String.valueOf(bill.getTotalBill()));
                labelStatus.setText(bill.getStatus());
            } else {
                labelUnits.setText("");
                labelTotalBill.setText("");
                labelStatus.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            try {
                BillService.getInstance().payBill(meter, (String) cmonth.getSelectedItem());
                Desktop.getDesktop().browse(new java.net.URI("https://ke.com.pk/bills-e-payments/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new PayBill("");
    }
}
