package ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import models.Appointment;
import models.Patient;
import models.User;

public class AppointmentFormDialog extends JDialog {
    private final List<Patient> patients;
    private final List<User> staffUsers;
    private JComboBox<Patient> patientBox;
    private JComboBox<User> staffBox;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField reasonField;
    private Appointment result;
    private int appointmentId;

    public AppointmentFormDialog(Frame owner, String title, Appointment existingAppointment, List<Patient> patients, List<User> staffUsers) {
        super(owner, title, true);
        this.patients = patients;
        this.staffUsers = staffUsers;
        this.appointmentId = existingAppointment != null ? existingAppointment.getId() : 0;
        buildUI(existingAppointment);
    }

    private void buildUI(Appointment existingAppointment) {
        setSize(520, 320);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBackground(UIStyle.CARD);

        JLabel patientLabel = new JLabel("Patient");
        JLabel staffLabel = new JLabel("Assigned Staff");
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
        JLabel timeLabel = new JLabel("Time (HH:MM:SS)");
        JLabel reasonLabel = new JLabel("Reason");

        UIStyle.styleLabel(patientLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(staffLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(dateLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(timeLabel, UIStyle.BODY_FONT, UIStyle.TEXT);
        UIStyle.styleLabel(reasonLabel, UIStyle.BODY_FONT, UIStyle.TEXT);

        patientBox = new JComboBox<>(patients.toArray(new Patient[0]));
        staffBox = new JComboBox<>(staffUsers.toArray(new User[0]));

        dateField = new JTextField();
        timeField = new JTextField();
        reasonField = new JTextField();

        UIStyle.styleTextField(dateField);
        UIStyle.styleTextField(timeField);
        UIStyle.styleTextField(reasonField);

        if (existingAppointment != null) {
            patientBox.setSelectedItem(existingAppointment.getPatient());
            staffBox.setSelectedItem(existingAppointment.getStaffUser());
            dateField.setText(existingAppointment.getAppointmentDate());
            timeField.setText(existingAppointment.getAppointmentTime());
            reasonField.setText(existingAppointment.getReason());
        }

        form.add(patientLabel);
        form.add(patientBox);
        form.add(staffLabel);
        form.add(staffBox);
        form.add(dateLabel);
        form.add(dateField);
        form.add(timeLabel);
        form.add(timeField);
        form.add(reasonLabel);
        form.add(reasonField);

        JPanel buttons = new JPanel();
        buttons.setBackground(UIStyle.CARD);

        javax.swing.JButton save = new javax.swing.JButton("Save");
        javax.swing.JButton cancel = new javax.swing.JButton("Cancel");

        UIStyle.styleButton(save);
        UIStyle.styleSecondaryButton(cancel);

        save.addActionListener(e -> saveAppointment());
        cancel.addActionListener(e -> dispose());

        buttons.add(save);
        buttons.add(cancel);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void saveAppointment() {
        Patient patient = (Patient) patientBox.getSelectedItem();
        User staffUser = (User) staffBox.getSelectedItem();
        String date = dateField.getText().trim();
        String time = timeField.getText().trim();
        String reason = reasonField.getText().trim();

        if (patient == null || staffUser == null || date.isEmpty() || time.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all appointment fields.");
            return;
        }

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Date must be in YYYY-MM-DD format.");
            return;
        }

        if (!time.matches("\\d{2}:\\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Time must be in HH:MM:SS format.");
            return;
        }

        result = new Appointment(appointmentId, patient, staffUser, date, time, reason);
        dispose();
    }

    public Appointment getResult() {
        return result;
    }
}