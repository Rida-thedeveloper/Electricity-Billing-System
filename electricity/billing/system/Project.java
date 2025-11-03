package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project extends JFrame implements ActionListener {

    private String atype, meter;

    public Project(String atype, String meter) {
        this.atype = atype;
        this.meter = meter;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Electricity Billing System");

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/elect1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1550, 850, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(i2));
        setContentPane(background);
        setLayout(null);

        // Menu bar
        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);

        // Admin Menu
        JMenu master = new JMenu("Admin Panel");
        master.setForeground(Color.BLUE);
        JMenuItem newCustomer = createMenuItem("New Customer", "icon/icon1.png", KeyEvent.VK_D, 'D');
        JMenuItem customerDetails = createMenuItem("Customer Details", "icon/icon2.png", KeyEvent.VK_M, 'M');
        JMenuItem depositDetails = createMenuItem("Deposit Details", "icon/icon3.png", KeyEvent.VK_N, 'N');
        JMenuItem calculateBill = createMenuItem("Calculate Bill", "icon/icon5.png", KeyEvent.VK_B, 'B');
        master.add(newCustomer);
        master.add(customerDetails);
        master.add(depositDetails);
        master.add(calculateBill);

        // Information Menu
        JMenu info = new JMenu("Information");
        info.setForeground(Color.BLUE);
        JMenuItem updateInfo = createMenuItem("Update Information", "icon/icon4.png", KeyEvent.VK_P, 'P');
        JMenuItem viewInfo = createMenuItem("View Information", "icon/icon6.png", KeyEvent.VK_L, 'L');
        info.add(updateInfo);
        info.add(viewInfo);

        // User Menu
        JMenu user = new JMenu("Bill");
        user.setForeground(Color.BLUE);
        JMenuItem payBill = createMenuItem("Pay Bill", "icon/icon4.png", KeyEvent.VK_R, 'R');
        JMenuItem billDetails = createMenuItem("Bill Details", "icon/icon6.png", KeyEvent.VK_B, 'B');
        user.add(payBill);
        user.add(billDetails);

        // Report Menu
        JMenu report = new JMenu("Bill Generation");
        report.setForeground(Color.BLUE);
        JMenuItem generateBill = createMenuItem("Generate Bill", "icon/icon7.png", KeyEvent.VK_G, 'G');
        report.add(generateBill);


        // Exit Menu
        JMenu mexit = new JMenu("Exit");
        mexit.setForeground(Color.BLUE);
        JMenuItem exit = createMenuItem("Exit", "icon/icon11.png", KeyEvent.VK_W, 'W');
        mexit.add(exit);

        // Add menus based on user type
        if (atype.equalsIgnoreCase("Admin")) {
            mb.add(master);
        } else {
            mb.add(info);
            mb.add(user);
            mb.add(report);
        }
//        mb.add(utility);
        mb.add(mexit);

        setVisible(true);
    }

    // Helper method to create menu items with icons, mnemonics, and accelerators
    private JMenuItem createMenuItem(String text, String iconPath, int keyEvent, char mnemonic) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("monospaced", Font.PLAIN, 12));
        item.setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        item.setIcon(new ImageIcon(img));

        item.setMnemonic(mnemonic);
        item.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ActionEvent.CTRL_MASK));
        item.addActionListener(this);

        return item;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();

        switch (msg) {
            case "New Customer": new NewCustomer(); break;
            case "Customer Details": new CustomerDetails(); break;
            case "Deposit Details": new DepositDetails(); break;
            case "Calculate Bill": new CalculateBill(); break;
            case "View Information": new ViewInformation(meter); break;
            case "Update Information": new UpdateInformation(meter); break;
            case "Bill Details": new BillDetails(meter); break;
            case "Pay Bill": new PayBill(meter); break;
            case "Generate Bill": new GenerateBill(meter); break;
            case "Exit":
                setVisible(false);
                new Login();
                break;
        }
    }

    public static void main(String[] args) {
        new Project("", ""); // Default guest user
    }
}
