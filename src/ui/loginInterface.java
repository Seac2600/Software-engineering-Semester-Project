package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import models.User;
import services.adminMangment;
import services.appointmentManagement;
import services.loginLogic;
import services.patientManagement;

@SuppressWarnings("serial")
public class loginInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private final transient loginLogic loginService;
    private final transient adminMangment adminService;
    private final transient patientManagement patientService;
    private final transient appointmentManagement appointmentService;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton forgotPasswordButton;
    private int failedLoginAttempts = 0;

    public loginInterface(loginLogic loginService, adminMangment adminService, patientManagement patientService,
                          appointmentManagement appointmentService) {
        this.loginService = loginService;
        this.adminService = adminService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        buildUI();
    }

    private void buildUI() {
        setTitle("Dental Office Login");
        setSize(1050, 728);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIStyle.BACKGROUND);
        setLayout(new BorderLayout());

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(UIStyle.BACKGROUND);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel cardPanel = new JPanel(new BorderLayout(0, 18));
        UIStyle.styleCard(cardPanel);

        JPanel titlePanel = new JPanel(new BorderLayout(0, 12));
        titlePanel.setBackground(UIStyle.CARD);

        ImageIcon logoIcon = loadLogoIcon();
        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);

        JLabel welcomeLabel = new JLabel("Welcome Back", SwingConstants.CENTER);
        JLabel subtitleLabel = new JLabel("Sign in to access your dashboard.", SwingConstants.CENTER);
        JLabel demoLabel = new JLabel("Demo Administrator account: Edward@mail.com / Edward123", SwingConstants.CENTER);
        JLabel demoLabel2 = new JLabel("Demo Dentist accounts: adam@mail.com / adam456", SwingConstants.CENTER);
        JLabel demoLabel3 = new JLabel("Demo Receptionist accounts: Elisha@mail.com / Elisha789", SwingConstants.CENTER);
        UIStyle.styleLabel(welcomeLabel, UIStyle.HEADING_FONT, UIStyle.PRIMARY_DARK);
        UIStyle.styleLabel(subtitleLabel, UIStyle.BODY_FONT, UIStyle.SUBTLE);
        UIStyle.styleLabel(demoLabel, UIStyle.SMALL_FONT, UIStyle.PRIMARY_DARK);
        UIStyle.styleLabel(demoLabel2, UIStyle.SMALL_FONT, UIStyle.PRIMARY_DARK);
        UIStyle.styleLabel(demoLabel3, UIStyle.SMALL_FONT, UIStyle.PRIMARY_DARK);

        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        titleTextPanel.setBackground(UIStyle.CARD);
        titleTextPanel.add(welcomeLabel);
        titleTextPanel.add(subtitleLabel);

        titlePanel.add(logoLabel, BorderLayout.NORTH);
        titlePanel.add(titleTextPanel, BorderLayout.CENTER);

        JPanel demoPanel = new JPanel(new GridLayout(3, 1, 0, 4));
        demoPanel.setBackground(UIStyle.CARD);

        demoPanel.add(demoLabel);
        demoPanel.add(demoLabel2);
        demoPanel.add(demoLabel3);

        titlePanel.add(demoPanel, BorderLayout.SOUTH);
    
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
        formPanel.add(emailLabel); formPanel.add(emailField);
        formPanel.add(passwordLabel); formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIStyle.CARD);
        JButton loginButton = new JButton("Login");
        UIStyle.styleButton(loginButton);
        loginButton.addActionListener(e -> handleLogin());
        buttonPanel.add(loginButton);

        forgotPasswordButton = new JButton("Forgot Password?");
        UIStyle.styleSecondaryButton(forgotPasswordButton);
        forgotPasswordButton.setVisible(false);
        forgotPasswordButton.addActionListener(e -> openForgotPasswordDialog());
        buttonPanel.add(forgotPasswordButton);

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
            failedLoginAttempts++;
            if (failedLoginAttempts >= 5) {
                forgotPasswordButton.setVisible(true);
                JOptionPane.showMessageDialog(this,
                    "Login failed. Invalid email or password.\n" );
            } else {
                JOptionPane.showMessageDialog(this,
                    "Login failed. Invalid email or password.\n");
            }
            return;
        }

        failedLoginAttempts = 0;
        String roleName = loggedInUser.getRole().getRoleName().toUpperCase();
        JOptionPane.showMessageDialog(this, "Welcome, " + loggedInUser.getFirstName() + "!");
        switch (roleName) {
            case "ADMIN" -> new adminInterface(adminService).setVisible(true);
            case "RECEPTIONIST" -> new receptionistInterface(patientService, appointmentService, adminService).setVisible(true);
            case "DENTIST" -> new dentistInterface(patientService, appointmentService, adminService).setVisible(true);
            default -> JOptionPane.showMessageDialog(this, "No dashboard is configured for this role yet.");
        }
        dispose();
    }

    private ImageIcon loadLogoIcon() {
        String resourcePath = "/resources/OrthocoreLogo.png";
        URL logoUrl = getClass().getResource(resourcePath);
        ImageIcon icon;
        if (logoUrl != null) {
            icon = new ImageIcon(logoUrl);
        } else {
            icon = new ImageIcon("src/resources/OrthocoreLogo.png");
        }

        int maxWidth = 180;
        int maxHeight = 180;
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        if (width > 0 && height > 0 && (width > maxWidth || height > maxHeight)) {
            double scaleFactor = Math.min((double) maxWidth / width, (double) maxHeight / height);
            int newWidth = (int) Math.round(width * scaleFactor);
            int newHeight = (int) Math.round(height * scaleFactor);
            Image scaled = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaled);
        }
        return icon;
    }

    private void openForgotPasswordDialog() {
        ForgotPasswordDialog dialog = new ForgotPasswordDialog(this, loginService);
        dialog.setVisible(true);
    }
}
