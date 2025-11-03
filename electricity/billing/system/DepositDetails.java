package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class DepositDetails extends JFrame implements ActionListener {

    private JComboBox<String> meternumber;  // only meter number
    private JTable table;
    private JButton search, print, refresh;

    public DepositDetails() {
        super("Deposit Details");

        // 🔹 Frame Setup
        setSize(900, 700);
        setLocation(320, 100);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 248, 255));

        // 🔹 Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(64, 105, 255));
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JLabel title = new JLabel("Deposit Details");
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        // 🔹 Top Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridBagLayout());
        filterPanel.setBackground(new Color(245, 248, 255));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblMeter = new JLabel("Select Meter No:");
        lblMeter.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblMeter.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(lblMeter, gbc);

        meternumber = new JComboBox<>();
        meternumber.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        meternumber.setBackground(Color.WHITE);
        meternumber.setPreferredSize(new Dimension(180, 30));
        populateMeterNumbers();
        gbc.gridx = 1; gbc.gridy = 0;
        filterPanel.add(meternumber, gbc);

        search = createStyledButton("Search");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        filterPanel.add(search, gbc);

        print = createStyledButton("Print");
        gbc.gridx = 1; gbc.gridy = 1;
        filterPanel.add(print, gbc);

        refresh = createStyledButton("Refresh");
        gbc.gridx = 2; gbc.gridy = 1;
        filterPanel.add(refresh, gbc);

        add(filterPanel, BorderLayout.NORTH);

        // 🔹 Table
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setGridColor(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(new Color(60, 60, 60));
        table.setSelectionBackground(new Color(200, 220, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setFillsViewportHeight(true);

        populateTable();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        sp.getViewport().setBackground(Color.WHITE);
        add(sp, BorderLayout.CENTER);

        // 🔹 Button Listeners
        search.addActionListener(this);
        print.addActionListener(this);
        refresh.addActionListener(this);

        setVisible(true);
    }

    // 🟢 Create styled buttons
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(64, 105, 255));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
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

    // 🧾 Populate meter numbers
    private void populateMeterNumbers() {
        try {
            for (String meter : CustomerDAO.getInstance().getAllMeterNumbers()) {
                meternumber.addItem(meter);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading meter numbers", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 🧾 Populate table with all bills
    private void populateTable() {
        try {
            ResultSet rs = BillDAO.getInstance().getAllBills();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading bill details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 🎯 Actions
    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();

        if (src == search) {
            try {
                // Month removed; only meter number
                ResultSet rs = BillDAO.getInstance().getBillByMeter(
                        (String) meternumber.getSelectedItem()
                );
                table.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error fetching search results", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == print) {
            try {
                table.print();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to print", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == refresh) {
            populateTable();
        }
    }

    public static void main(String[] args) {
        new DepositDetails();
    }
}
