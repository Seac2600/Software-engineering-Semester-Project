package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.Appointment;
import services.adminMangment;
import services.appointmentManagement;
import services.patientManagement;

public class appointmentInterface extends BaseDashboard {
    private final appointmentManagement appointmentService;
    private final patientManagement patientService;
    private final adminMangment adminService;
    private final boolean editable;
    private DefaultTableModel tableModel;
    private JTable appointmentTable;

    public appointmentInterface(appointmentManagement appointmentService, patientManagement patientService,
                                adminMangment adminService, boolean editable, String title, String subtitle) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.adminService = adminService;
        this.editable = editable;
        buildBase(title, subtitle);
        buildUI();
        loadAppointments();
    }

    private void buildUI() {
        JLabel sectionLabel = new JLabel("Appointments", SwingConstants.LEFT);
        sectionLabel.setFont(UIStyle.HEADING_FONT);
        sectionLabel.setForeground(UIStyle.TEXT);
        contentPanel.add(sectionLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Patient", "Assigned Staff", "Date", "Time", "Reason"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        appointmentTable = new JTable(tableModel);
        appointmentTable.setRowHeight(28);
        contentPanel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(UIStyle.CARD);
        if (editable) {
            JButton addButton = new JButton("Add Appointment");
            JButton editButton = new JButton("Edit Selected");
            JButton deleteButton = new JButton("Delete Selected");
            UIStyle.styleButton(addButton);
            UIStyle.styleSecondaryButton(editButton);
            UIStyle.styleDangerButton(deleteButton);
            addButton.addActionListener(e -> addAppointment());
            editButton.addActionListener(e -> editAppointment());
            deleteButton.addActionListener(e -> deleteAppointment());
            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
        }
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadAppointments() {
        tableModel.setRowCount(0);
        List<Appointment> appointments = appointmentService.getAppointments();
        for (Appointment appointment : appointments) {
            tableModel.addRow(new Object[]{
                appointment.getId(),
                appointment.getPatient().getFullName(),
                appointment.getStaffUser().getFullName(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getReason()
            });
        }
    }

    private void addAppointment() {
        AppointmentFormDialog dialog = new AppointmentFormDialog(this, "Add Appointment", null,
            patientService.getPatients(), adminService.getStaffUsers());
        dialog.setVisible(true);
        Appointment appointment = dialog.getResult();
        if (appointment != null && appointmentService.addAppointment(appointment)) {
            loadAppointments();
            JOptionPane.showMessageDialog(this, "Appointment added successfully.");
        }
    }

    private void editAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment first.");
            return;
        }
        int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);
        Appointment existing = appointmentService.findAppointmentById(appointmentId);
        AppointmentFormDialog dialog = new AppointmentFormDialog(this, "Edit Appointment", existing,
            patientService.getPatients(), adminService.getStaffUsers());
        dialog.setVisible(true);
        Appointment updated = dialog.getResult();
        if (updated != null) {
            updated.setId(appointmentId);
            if (appointmentService.editAppointment(updated)) {
                loadAppointments();
                JOptionPane.showMessageDialog(this, "Appointment updated successfully.");
            }
        }
    }

    private void deleteAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment first.");
            return;
        }
        int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);
        if (appointmentService.deleteAppointment(appointmentId)) {
            loadAppointments();
            JOptionPane.showMessageDialog(this, "Appointment deleted successfully.");
        }
    }
}
