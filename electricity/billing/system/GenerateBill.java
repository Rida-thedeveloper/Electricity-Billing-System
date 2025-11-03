package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GenerateBill extends JFrame implements ActionListener {

    private String meter;
    private JButton bill, print, close;
    private Choice cmonth;
    private JTextArea area;

    public GenerateBill(String meter) {
        this.meter = meter;

        setTitle("Electricity Bill Generator");
        setSize(600, 750);
        setLocation(500, 20);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔹 HEADER PANEL
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        headerPanel.setBackground(new Color(30, 60, 114)); // Navy blue
        JLabel heading = new JLabel("⚡ Electricity Bill Generator");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JLabel meternumber = new JLabel("Meter No: " + meter);
        meternumber.setForeground(Color.WHITE);
        meternumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        headerPanel.add(heading);
        headerPanel.add(meternumber);

        // 🔹 MONTH PANEL
        JPanel monthPanel = new JPanel();
        monthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        monthPanel.setBackground(new Color(240, 245, 255));

        JLabel monthLabel = new JLabel("Select Month:");
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        monthPanel.add(monthLabel);

        cmonth = new Choice();
        String[] months = {"January","February","March","April","May","June","July","August",
                "September","October","November","December"};
        for (String m : months) cmonth.add(m);
        monthPanel.add(cmonth);

        // 🔹 BUTTONS
        bill = createButton("Generate Bill");
        bill.addActionListener(this);
        monthPanel.add(bill);

        print = createButton("Print Bill");
        print.addActionListener(this);
        monthPanel.add(print);

        close = createButton("Close");
        close.addActionListener(this);
        monthPanel.add(close);

        // 🔹 BILL DISPLAY AREA
        area = new JTextArea(40, 15);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        area.setText("\n\n\t Click 'Generate Bill' to view your details");
        area.setBackground(new Color(250, 250, 250));

        JScrollPane pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 🔹 ADD COMPONENTS
        add(headerPanel, BorderLayout.NORTH);
        add(monthPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(pane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 120, 170));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 130, 180));
            }
        });
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();

        if (source == bill) {
            generateBill();
        }
        else if (source == print) {
            try {
                boolean done = area.print();
                if (done) {
                    JOptionPane.showMessageDialog(this, "Bill printed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Printing canceled!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error while printing: " + e.getMessage());
            }
        }
        else if (source == close) {
            setVisible(false);
        }
    }

    private void generateBill() {
        try {
            String month = cmonth.getSelectedItem();

            // 🧾 Header Section
            area.setText("");
            area.append("\n\t--------------------------------------------");
            area.append("\n\t      K-ELECTRIC LIMITED");
            area.append("\n\t--------------------------------------------");
            area.append("\n\t   ELECTRICITY BILL FOR " + month.toUpperCase() + ", 2025");
            area.append("\n\t--------------------------------------------\n");

            // ✅ Check if Bill Exists FIRST
            Bill billData = BillDAO.getInstance().getBillByMeterAndMonth(meter, month);

            if (billData == null) {
                area.append("\n\n⚠️  No Bill Found for this Month!");
                area.append("\n--------------------------------------------");
                area.append("\nYou cannot pay the bill because no record exists.");
                return;
            }

            // CUSTOMER DETAILS
            area.append("\n\n  ⚙️  CUSTOMER DETAILS\n");
            area.append("  --------------------------------------------\n");
            Customer customer = CustomerDAO.getInstance().getCustomerByMeter(meter);

            if (customer == null) {
                JOptionPane.showMessageDialog(this,
                        "Customer details not found!\nBill cannot be generated.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                area.setText("Unable to generate bill.\nNo customer details found for this meter number.");
                return;
            }

            area.append(String.format("  Name           : %s%n", customer.getName()));
            area.append(String.format("  Meter Number   : %s%n", customer.getMeter()));
            area.append(String.format("  Address        : %s%n", customer.getAddress()));
            area.append(String.format("  City           : %s%n", customer.getCity()));
            area.append(String.format("  State          : %s%n", customer.getState()));
            area.append(String.format("  Email          : %s%n", customer.getEmail()));
            area.append(String.format("  Phone          : %s%n", customer.getPhone()));

            // METER INFO
            area.append("\n\n   METER INFORMATION\n");
            area.append("  --------------------------------------------\n");
            MeterInfoData meterInfo = MeterInfoDAO.getInstance().getMeterInfoByMeter(meter);
            if (meterInfo != null) {
                area.append(String.format("  Location       : %s%n", meterInfo.getMeterLocation()));
                area.append(String.format("  Type           : %s%n", meterInfo.getMeterType()));
                area.append(String.format("  Phase Code     : %s%n", meterInfo.getPhaseCode()));
                area.append(String.format("  Bill Type      : %s%n", meterInfo.getBillType()));
                area.append(String.format("  Days Active    : %s%n", meterInfo.getDays()));
            } else {
                area.append("  Meter Info Not Found!\n");
            }

            // TAX INFO
            area.append("\n\n   TAX AND CHARGES\n");
            area.append("  --------------------------------------------\n");

            Tax tax = TaxDAO.getInstance().getTax();
            if (tax != null) {
                area.append(String.format("  Cost Per Unit  : %.2f%n", tax.getCostPerUnit()));
                area.append(String.format("  Meter Rent     : %.2f%n", tax.getMeterRent()));
                area.append(String.format("  Service Charge : %.2f%n", tax.getServiceCharge()));
                area.append(String.format("  Service Tax    : %.2f%n", tax.getServiceTax()));
                area.append(String.format("  Kelectric Tax  : %.2f%n", tax.getKelectricTax()));
                area.append(String.format("  Fixed Tax      : %.2f%n", tax.getFixedTax()));
            }

            // BILL SUMMARY
            area.append("\n\n   BILL SUMMARY\n");
            area.append("  --------------------------------------------\n");
            area.append(String.format("  Current Month  : %s%n", billData.getMonth()));
            area.append(String.format("  Units Consumed : %s%n", billData.getUnits()));
            area.append(String.format("  Total Charges  : Rs. %s%n", billData.getTotalBill()));
            area.append("  --------------------------------------------\n");
            area.append(String.format("  💵 Total Payable : Rs. %s%n", billData.getTotalBill()));
            area.append("  --------------------------------------------\n");
            area.append("\n  Thank you for choosing K-Electric ⚡");
            area.append("\n  Visit us: www.kelectric.com\n");

        } catch (Exception e) {
            e.printStackTrace();
            area.setText(" Error Generating Bill: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new GenerateBill("12345");
    }
}
