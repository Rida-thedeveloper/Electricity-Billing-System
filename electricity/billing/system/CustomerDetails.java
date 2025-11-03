package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class CustomerDetails extends JFrame implements ActionListener {

    private JTable table;
    private JButton print, refresh;

    public CustomerDetails() {
        super("Customer Details");

        // Frame styling
        setSize(1100, 650);
        setLocation(220, 120);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(245, 248, 255));

        // 🟣 Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(64, 105, 255));
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JLabel title = new JLabel("Customer Details");
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);

        // 🟩 Table Setup
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setGridColor(new Color(230, 230, 230));
        table.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(new Color(60, 60, 60));
        table.setSelectionBackground(new Color(200, 220, 255));
        table.setSelectionForeground(Color.BLACK);

        populateTable();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sp.getViewport().setBackground(Color.WHITE);
        add(sp, BorderLayout.CENTER);

        // 🟠 Bottom Button Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245, 248, 255));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        print = createStyledButton("Print");
        refresh = createStyledButton("Refresh");

        print.addActionListener(this);
        refresh.addActionListener(this);

        bottomPanel.add(print);
        bottomPanel.add(refresh);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // 🧠 Custom reusable button style
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(64, 105, 255));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(45, 85, 230));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(64, 105, 255));
            }
        });
        return btn;
    }

    private void populateTable() {
        try {
            ResultSet rs = CustomerDAO.getInstance().getAllCustomersResultSet();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading customer data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to print", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == refresh) {
            populateTable();
        }
    }

    public static void main(String[] args) {
        new CustomerDetails();
    }
}
