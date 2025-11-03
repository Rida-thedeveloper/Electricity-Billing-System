package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.*;

// Modern styled BillDetails screen
public class BillDetails extends JFrame {

    private JTable table;

    public BillDetails(String meter) {
        setTitle("Electricity Bill Details");
        setSize(900, 650);
        setLocation(300, 120);
        setLayout(null);

        // Background
        JPanel background = new JPanel();
        background.setLayout(null);
        background.setBackground(new Color(245, 247, 250));
        background.setBounds(0, 0, 900, 650);
        add(background);

        // Header panel
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 204));
        header.setBounds(0, 0, 900, 70);
        header.setLayout(null);
        background.add(header);

        JLabel title = new JLabel("Electricity Bill Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(300, 15, 400, 40);
        header.add(title);

        // Table panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBounds(50, 100, 800, 460);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2, true));
        background.add(tablePanel);

        // Create table
        table = new JTable();
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(230, 240, 255));
        table.getTableHeader().setForeground(new Color(0, 51, 102));
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(204, 229, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Add scrollpane
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(Color.WHITE);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.add(sp, BorderLayout.CENTER);

        // Load data
        addTableData(meter);

        // Footer
        JLabel footer = new JLabel("Double-check your records before payment.");
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        footer.setForeground(new Color(100, 100, 100));
        footer.setBounds(300, 580, 350, 30);
        background.add(footer);

        setVisible(true);
    }

    // Load data into table
    private void addTableData(String meter) {
        try {
            ResultSet rs = BillDAO.getInstance().getBillByMeter(meter);
            table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));

            // Auto resize columns for better fit
            TableColumnModel columnModel = table.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setPreferredWidth(120);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " Error fetching bill details", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BillDetails("");
    }
}
