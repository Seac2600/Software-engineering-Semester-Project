package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import services.loginLogic;

@SuppressWarnings("serial")
public class ForgotPasswordDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private final transient loginLogic loginService;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ForgotPasswordDialog(JFrame owner, loginLogic loginService) {
        super(owner, "Forgot Password", true);
        this.loginService = loginService;
        setModal(true);
        setFocusableWindowState(true);
        setAutoRequestFocus(true);
        setAlwaysOnTop(true);
        buildUI();
    }

    private void buildUI() {
        setSize(620, 560);
        setLocationRelativeTo(getOwner());
        setResizable(false);
        getContentPane().setBackground(UIStyle.BACKGROUND);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(UIStyle.BACKGROUND);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel cardPanel = new JPanel(new BorderLayout(0, 18));
        UIStyle.styleCard(cardPanel);

        JPanel titlePanel = new JPanel(new BorderLayout(0, 8));
        titlePanel.setBackground(UIStyle.CARD);
        JLabel titleLabel = new JLabel("Reset Your Password", SwingConstants.CENTER);
        JLabel subtitleLabel = new JLabel("<html><div style='text-align:center; font-size:13px;'>Enter your email and exact first/last name to verify your identity.</div></html>", SwingConstants.CENTER);
        UIStyle.styleLabel(titleLabel, UIStyle.HEADING_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(subtitleLabel, UIStyle.BODY_FONT, UIStyle.SUBTLE);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 14, 14));
        formPanel.setBackground(UIStyle.CARD);
        JLabel emailLabel = new JLabel("Email");
        JLabel firstNameLabel = new JLabel("First Name");
        JLabel lastNameLabel = new JLabel("Last Name");
        JLabel newPasswordLabel = new JLabel("New Password");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
        UIStyle.styleLabel(emailLabel, fieldFont, UIStyle.TEXT);
        UIStyle.styleLabel(firstNameLabel, fieldFont, UIStyle.TEXT);
        UIStyle.styleLabel(lastNameLabel, fieldFont, UIStyle.TEXT);
        UIStyle.styleLabel(newPasswordLabel, fieldFont, UIStyle.TEXT);
        UIStyle.styleLabel(confirmPasswordLabel, fieldFont, UIStyle.TEXT);

        emailField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        emailField.setFont(fieldFont);
        firstNameField.setFont(fieldFont);
        lastNameField.setFont(fieldFont);
        newPasswordField.setFont(fieldFont);
        confirmPasswordField.setFont(fieldFont);
        emailField.setColumns(18);
        firstNameField.setColumns(18);
        lastNameField.setColumns(18);
        newPasswordField.setColumns(18);
        confirmPasswordField.setColumns(18);
        emailField.setEditable(true);
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        newPasswordField.setEditable(true);
        confirmPasswordField.setEditable(true);
        emailField.setEnabled(true);
        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        newPasswordField.setEnabled(true);
        confirmPasswordField.setEnabled(true);
        emailField.setFocusable(true);
        firstNameField.setFocusable(true);
        lastNameField.setFocusable(true);
        newPasswordField.setFocusable(true);
        confirmPasswordField.setFocusable(true);
        emailField.setRequestFocusEnabled(true);
        firstNameField.setRequestFocusEnabled(true);
        lastNameField.setRequestFocusEnabled(true);
        newPasswordField.setRequestFocusEnabled(true);
        confirmPasswordField.setRequestFocusEnabled(true);
        emailField.setFocusTraversalKeysEnabled(true);
        firstNameField.setFocusTraversalKeysEnabled(true);
        lastNameField.setFocusTraversalKeysEnabled(true);
        newPasswordField.setFocusTraversalKeysEnabled(true);
        confirmPasswordField.setFocusTraversalKeysEnabled(true);
        emailField.setOpaque(true);
        firstNameField.setOpaque(true);
        lastNameField.setOpaque(true);
        newPasswordField.setOpaque(true);
        confirmPasswordField.setOpaque(true);
        UIStyle.styleTextField(emailField);
        UIStyle.styleTextField(firstNameField);
        UIStyle.styleTextField(lastNameField);
        UIStyle.styleTextField(newPasswordField);
        UIStyle.styleTextField(confirmPasswordField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(newPasswordLabel);
        formPanel.add(newPasswordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);

        JPanel notePanel = new JPanel(new GridLayout(1, 2, 0, 0));
        notePanel.setBackground(UIStyle.CARD);
        notePanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        notePanel.add(new JLabel());
        JLabel noteLabel = new JLabel("Enter your new password.");
        noteLabel.setHorizontalAlignment(SwingConstants.LEFT);
        noteLabel.setVerticalAlignment(SwingConstants.TOP);
        UIStyle.styleLabel(noteLabel, UIStyle.BODY_FONT, UIStyle.SUBTLE);
        notePanel.add(noteLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIStyle.CARD);
        JButton resetButton = new JButton("Reset Password");
        JButton cancelButton = new JButton("Cancel");
        UIStyle.styleButton(resetButton);
        UIStyle.styleSecondaryButton(cancelButton);
        resetButton.setFont(UIStyle.BODY_FONT);
        cancelButton.setFont(UIStyle.BODY_FONT);
        resetButton.addActionListener(e -> handleReset());
        cancelButton.addActionListener(e -> dispose());
        emailField.addActionListener(e -> handleReset());
        firstNameField.addActionListener(e -> handleReset());
        lastNameField.addActionListener(e -> handleReset());
        newPasswordField.addActionListener(e -> handleReset());
        confirmPasswordField.addActionListener(e -> handleReset());
        getRootPane().setDefaultButton(resetButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        JPanel contentPanel = new JPanel(new BorderLayout(0, 12));
        contentPanel.setBackground(UIStyle.CARD);
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(notePanel, BorderLayout.SOUTH);

        cardPanel.add(titlePanel, BorderLayout.NORTH);
        cardPanel.add(contentPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);
        outerPanel.add(cardPanel, BorderLayout.CENTER);
        add(outerPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                SwingUtilities.invokeLater(() -> emailField.requestFocusInWindow());
            }
        });

        SwingUtilities.invokeLater(() -> emailField.requestFocusInWindow());
    }

    private void handleReset() {
        String email = emailField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all fields.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "New password and confirmation must match.");
            return;
        }

        boolean success = loginService.resetPassword(email, firstName, lastName, newPassword);
        if (!success) {
            JOptionPane.showMessageDialog(this,
                "Could not reset password. Make sure the provided email and names match an existing account.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Password reset successfully. You can now sign in with your new password.");
        dispose();
    }
}
