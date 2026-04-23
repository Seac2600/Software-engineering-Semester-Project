package ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import models.Patient;

public class PatientFormDialog extends JDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JTextField dobField;
    private JTextField addressField;
    private JTextArea notesArea;
    private Patient result;
    private int patientId;

    public PatientFormDialog(Frame owner, String title, Patient existingPatient) {
        super(owner, title, true);
        this.patientId = existingPatient != null ? existingPatient.getId() : 0;
        buildUI(existingPatient);
    }

    private void buildUI(Patient existingPatient) {
        setSize(500, 430);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBackground(UIStyle.CARD);

        JLabel first = new JLabel("First Name");
        JLabel last = new JLabel("Last Name");
        JLabel phone = new JLabel("Phone");
        JLabel dob = new JLabel("Date of Birth (YYYY-MM-DD)");
        JLabel address = new JLabel("Address");
        JLabel notes = new JLabel("Notes");

        UIStyle.styleLabel(first, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(last, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(phone, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(dob, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(address, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(notes, UIStyle.BODY_FONT, UIStyle.TEXT);

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        phoneField = new JTextField();
        dobField = new JTextField();
        addressField = new JTextField();
        notesArea = new JTextArea(4, 20);

        UIStyle.styleTextField(firstNameField);
        UIStyle.styleTextField(lastNameField);
        UIStyle.styleTextField(phoneField);
        UIStyle.styleTextField(dobField);
        UIStyle.styleTextField(addressField);
        notesArea.setFont(UIStyle.BODY_FONT);

        if (existingPatient != null) {
            firstNameField.setText(existingPatient.getFirstName());
            lastNameField.setText(existingPatient.getLastName());
            phoneField.setText(existingPatient.getPhone());
            dobField.setText(existingPatient.getDateOfBirth());
            addressField.setText(existingPatient.getAddress());
            notesArea.setText(existingPatient.getNotes());
        }

        form.add(first);
        form.add(firstNameField);
        form.add(last);
        form.add(lastNameField);
        form.add(phone);
        form.add(phoneField);
        form.add(dob);
        form.add(dobField);
        form.add(address);
        form.add(addressField);
        form.add(notes);
        form.add(new javax.swing.JScrollPane(notesArea));

        JPanel buttons = new JPanel();
        buttons.setBackground(UIStyle.CARD);

        javax.swing.JButton save = new javax.swing.JButton("Save");
        javax.swing.JButton cancel = new javax.swing.JButton("Cancel");

        UIStyle.styleButton(save);
        UIStyle.styleSecondaryButton(cancel);

        save.addActionListener(e -> savePatient());
        cancel.addActionListener(e -> dispose());

        buttons.add(save);
        buttons.add(cancel);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void savePatient() {
    String firstName = firstNameField.getText().trim();
    String lastName = lastNameField.getText().trim();
    String phone = phoneField.getText().trim();
    String dob = dobField.getText().trim();
    String address = addressField.getText().trim();
    String notes = notesArea.getText().trim();

    if (firstName.isEmpty() || lastName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "First and last name are required.");
        return;
    }

    if (!phone.isEmpty() && !phone.matches("\\d{3}-\\d{3}-\\d{4}|\\d{10}")) {
        JOptionPane.showMessageDialog(this, "Phone must be in 123-456-7890 or 1234567890 format.");
        return;
    }

    if (!dob.isEmpty() && !dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
        JOptionPane.showMessageDialog(this, "Date of birth must be in YYYY-MM-DD format.");
        return;
    }

    if (address.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Address is required.");
        return;
    }

    if (notes.length() > 500) {
        JOptionPane.showMessageDialog(this, "Notes must be 500 characters or less.");
        return;
    }

    result = new Patient(
        patientId,
        firstName,
        lastName,
        phone,
        dob,
        address,
        notes
    );

    dispose();
}

    public Patient getResult() {
        return result;
    }
}