package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Signup extends JFrame implements ActionListener {

    private JButton create, back;
    private Choice accountType;
    private JTextField meter, username, name;
    private JPasswordField password;

    public Signup() {
        setBounds(450, 150, 700, 400);
        setLayout(null);

        // Background Panel (Navy)
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(30, 60, 114)); // Navy Blue
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(null);
        setContentPane(bgPanel);

        // LEFT IMAGE
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/signup (1).png"));
        Image i2 = i1.getImage().getScaledInstance(250, 230, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(20, 70, 250, 220);
        bgPanel.add(image);

        // FORM PANEL
        JPanel panel = new JPanel();
        panel.setBounds(300, 40, 360, 300);
        panel.setOpaque(false);
        panel.setLayout(null);
        bgPanel.add(panel);

        JLabel heading = new JLabel("Create Account As");
        styleLabel(heading, 20, 10, panel);

        accountType = new Choice();
        accountType.add("Admin");
        accountType.add("Customer");
        accountType.setBounds(180, 10, 160, 25);
        panel.add(accountType);

        JLabel lblmeter = new JLabel("Meter Number");
        styleLabel(lblmeter, 20, 50, panel);
        lblmeter.setVisible(false);

        meter = createRoundedTextField();
        meter.setBounds(180, 50, 160, 28);
        meter.setVisible(false);
        panel.add(meter);

        JLabel lblusername = new JLabel("Username");
        styleLabel(lblusername, 20, 90, panel);

        username = createRoundedTextField();
        username.setBounds(180, 90, 160, 28);
        panel.add(username);

        JLabel lblname = new JLabel("Name");
        styleLabel(lblname, 20, 130, panel);

        name = createRoundedTextField();
        name.setBounds(180, 130, 160, 28);
        panel.add(name);

        JLabel lblpassword = new JLabel("Password");
        styleLabel(lblpassword, 20, 170, panel);

        password = new JPasswordField();
        password.setBounds(180, 170, 160, 28);
        password.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.add(password);

        // 🔹 Toggle fields for Customer/Admin
        accountType.addItemListener(ae -> {
            boolean isCustomer = accountType.getSelectedItem().equals("Customer");
            lblmeter.setVisible(isCustomer);
            meter.setVisible(isCustomer);
            name.setEditable(!isCustomer);
        });

        // 🔹 Auto-fill name from database when meter number entered
        meter.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String smeter = meter.getText().trim();
                if (!smeter.isEmpty()) {
                    try {
                        Customer customer = CustomerDAO.getInstance().getCustomerByMeter(smeter);
                        if (customer != null) {
                            name.setText(customer.getName());
                        } else {
                            name.setText("");
                            JOptionPane.showMessageDialog(null, "⚠️ No customer found with this meter number.");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                    }
                }
            }
        });

        // BUTTONS
        create = createRoundedButton("Create");
        create.setBounds(50, 220, 120, 32);
        create.addActionListener(this);
        panel.add(create);

        back = createRoundedButton("Back");
        back.setBounds(190, 220, 120, 32);
        back.addActionListener(this);
        panel.add(back);

        setVisible(true);
    }

    private void styleLabel(JLabel label, int x, int y, JPanel parent) {
        label.setBounds(x, y, 140, 20);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        parent.add(label);
    }

    private JTextField createRoundedTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 120, 170));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            String atype = accountType.getSelectedItem();
            String susername = username.getText().trim();
            String sname = name.getText().trim();
            String spassword = new String(password.getPassword()).trim();
            String smeter = meter.getText().trim();

            // ✅ Validation
            String validationError = SignupValidator.validateSignupFields(atype, susername, sname, spassword, smeter);
            if (validationError != null) {
                JOptionPane.showMessageDialog(null, validationError);
                return;
            }

            try {
                if (LoginDAO.getInstance().isUsernameExists(susername)) {
                    JOptionPane.showMessageDialog(null, "❌ Username already exists. Please choose another one.");
                    return;
                }
                if (atype.equals("Admin")) {
                    LoginDAO.getInstance().createAdmin(susername, sname, spassword);
                } else if (atype.equals("Customer")) {
                    LoginDAO.getInstance().updateCustomerAccount(smeter, susername, spassword);
                }

                JOptionPane.showMessageDialog(null, "✅ Account Created Successfully!");
                setVisible(false);
                new Login();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
