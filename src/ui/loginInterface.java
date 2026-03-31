package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import models.User;
import services.adminMangment;
import services.loginLogic;

public class loginInterface extends JFrame {
    private loginLogic loginService;
    private adminMangment adminService;
    private JTextField emailField;
    private JPasswordField passwordField;

    public loginInterface(loginLogic loginService, adminMangment adminService) {
        this.loginService = loginService;
        this.adminService = adminService;
        buildUI();
    }

    private void buildUI() {
        setTitle("Dental Office System - Login");
        setSize(520, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIStyle.BACKGROUND);
        setLayout(new BorderLayout());

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(UIStyle.BACKGROUND);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel cardPanel = new JPanel(new BorderLayout(0, 18));
        UIStyle.styleCard(cardPanel);

        JPanel titlePanel = new JPanel(new BorderLayout(0, 8));
        titlePanel.setBackground(UIStyle.CARD);

        JLabel titleLabel = new JLabel("Dental Office Login", SwingConstants.CENTER);
        JLabel subtitleLabel = new JLabel("Sign in to access the system dashboard.", SwingConstants.CENTER);
        JLabel demoLabel = new JLabel("Use this login to test the login. Edward@mail.com / Edward123", SwingConstants.CENTER);

        UIStyle.styleLabel(titleLabel, UIStyle.TITLE_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(subtitleLabel, UIStyle.BODY_FONT, UIStyle.SUBTLE);
        UIStyle.styleLabel(demoLabel, UIStyle.SMALL_FONT, UIStyle.PRIMARY_DARK);

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        titlePanel.add(demoLabel, BorderLayout.SOUTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        formPanel.setBackground(UIStyle.CARD);

        JLabel emailLabel = new JLabel("Email");
        JLabel passwordLabel = new JLabel("Password");
        UIStyle.styleLabel(emailLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(passwordLabel, UIStyle.BODY_FONT, UIStyle.TEXT);

        emailField = new JTextField();
        passwordField = new JPasswordField();
        UIStyle.styleTextField(emailField);
        UIStyle.styleTextField(passwordField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIStyle.CARD);

        JButton loginButton = new JButton("Login");
        UIStyle.styleButton(loginButton);
        loginButton.addActionListener(e -> handleLogin());

        buttonPanel.add(loginButton);

        cardPanel.add(titlePanel, BorderLayout.NORTH);
        cardPanel.add(formPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        outerPanel.add(cardPanel, BorderLayout.CENTER);
        add(outerPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.");
            return;
        }

        User loggedInUser = loginService.authenticate(email, password);

        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(this, "Login failed. Invalid email or password.");
            return;
        }

        if (loggedInUser.getRole().getName().equalsIgnoreCase("Admin")) {
            JOptionPane.showMessageDialog(this, "Welcome, " + loggedInUser.getFirstName() + "!");
            adminInterface adminUI = new adminInterface(adminService);
            adminUI.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "This user logged in successfully, but the only dashboard i have set up is the admin's. We will have the otherss ready for sprint 3");
        }
    }
}
