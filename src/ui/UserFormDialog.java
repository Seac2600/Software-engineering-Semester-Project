package ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import models.User;
import models.roles;

public class UserFormDialog extends JDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private User userResult;
    private int userId;

    public UserFormDialog(Frame owner, String title, User existingUser, int nextId) {
        super(owner, title, true);
        this.userId = existingUser != null ? existingUser.getId() : nextId;
        buildUI(existingUser);
    }

    private void buildUI(User existingUser) {
        setSize(420, 320);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(UIStyle.CARD);

        JLabel firstLabel = new JLabel("First Name");
        JLabel lastLabel = new JLabel("Last Name");
        JLabel emailLabel = new JLabel("Email");
        JLabel passwordLabel = new JLabel("Password");
        JLabel roleLabel = new JLabel("Role");

        UIStyle.styleLabel(firstLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(lastLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(emailLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(passwordLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(roleLabel, UIStyle.BODY_FONT, UIStyle.TEXT);

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        roleBox = new JComboBox<>(new String[]{"Admin", "Dentist", "Receptionist"});

        UIStyle.styleTextField(firstNameField);
        UIStyle.styleTextField(lastNameField);
        UIStyle.styleTextField(emailField);
        UIStyle.styleTextField(passwordField);

        if (existingUser != null) {
            firstNameField.setText(existingUser.getFirstName());
            lastNameField.setText(existingUser.getLastName());
            emailField.setText(existingUser.getEmail());
            passwordField.setText(existingUser.getPassword());
            roleBox.setSelectedItem(existingUser.getRole().getName());
        }

        formPanel.add(firstLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastLabel);
        formPanel.add(lastNameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(roleLabel);
        formPanel.add(roleBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIStyle.CARD);

        javax.swing.JButton saveButton = new javax.swing.JButton("Save");
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        UIStyle.styleButton(saveButton);
        UIStyle.styleSecondaryButton(cancelButton);

        saveButton.addActionListener(e -> saveUser());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(saveButton);
    }

    private void saveUser() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String roleName = (String) roleBox.getSelectedItem();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        roles role;
        if ("Admin".equals(roleName)) {
            role = new roles(1, "Admin");
        } else if ("Dentist".equals(roleName)) {
            role = new roles(2, "Dentist");
        } else {
            role = new roles(3, "Receptionist");
        }

        userResult = new User(userId, firstName, lastName, email, password, role);
        dispose();
    }

    public User getUserResult() {
        return userResult;
    }
}