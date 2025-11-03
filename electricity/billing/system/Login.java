package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    private JButton login, cancel, signup;
    private JTextField username, password;
    private Choice logginin;

    public Login() {
        super("Login Page");
        setLayout(null);

        // Gradient Background Panel
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(30, 60, 114));  // Softer Navy Blue

                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        bgPanel.setLayout(null);
        setContentPane(bgPanel);

        // Modern Font
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        // Username field
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(300, 20, 100, 20);
        lblusername.setFont(font);
        lblusername.setForeground(Color.WHITE);  // <-- White Color Added
        add(lblusername);

        username = new JTextField();
        styleTextField(username);
        username.setBounds(400, 20, 150, 25);
        add(username);


        // Password field
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(300, 60, 100, 20);
        lblpassword.setFont(font);
        lblpassword.setForeground(Color.WHITE);
        add(lblpassword);

        password = new JPasswordField();
        styleTextField(password);
        password.setBounds(400, 60, 150, 25);
        add(password);


        // User type selection
        JLabel loggininas = new JLabel("Logging in as");
        loggininas.setBounds(300, 100, 100, 20);
        loggininas.setFont(font);
        loggininas.setForeground(Color.WHITE);
        add(loggininas);

        logginin = new Choice();
        logginin.add("Admin");
        logginin.add("Customer");
        logginin.setBounds(400, 100, 150, 25);
        add(logginin);

        // Buttons
        login = createRoundedButton("Login");
        login.setBounds(330, 160, 100, 30);
        login.addActionListener(this);
        add(login);

        cancel = createRoundedButton("Cancel");
        cancel.setBounds(450, 160, 100, 30);
        cancel.addActionListener(this);
        add(cancel);

        signup = createRoundedButton("Signup");
        signup.setBounds(380, 200, 100, 30);
        signup.addActionListener(this);
        add(signup);

        // Image
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/profile.png"));
        Image i8 = i7.getImage().getScaledInstance(300, 200, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i8));
        image.setBounds(20, 20, 250, 200);
        add(image);

        setSize(640, 300);
        setLocation(400, 200);
        setVisible(true);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Hover effect
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
        if (ae.getSource() == login) {
            String susername = username.getText();
            String spassword = new String(((JPasswordField) password).getPassword());
            String user = logginin.getSelectedItem();

            try {
                // Use LoginDAO for authentication (SRP)
                String meter = LoginDAO.getInstance().authenticate(susername, spassword, user);
                if (meter != null) {
                    setVisible(false);
                    new Project(user, meter);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login");
                    username.setText("");
                    password.setText("");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        } else if (ae.getSource() == signup) {
            setVisible(false);
            new Signup();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
