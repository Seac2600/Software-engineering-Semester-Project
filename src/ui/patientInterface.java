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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.Patient;
import services.patientManagement;

public class patientInterface extends BaseDashboard {
    private final patientManagement patientService;
    private final boolean editable;
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private JTextField searchField;

    public patientInterface(patientManagement patientService, boolean editable, String title, String subtitle) {
        this.patientService = patientService;
        this.editable = editable;
        buildBase(title, subtitle);
        buildUI();
        loadPatients(patientService.getPatients());
    }

    private void buildUI() {
        JLabel sectionLabel = new JLabel("Patient Records", SwingConstants.LEFT);
        sectionLabel.setFont(UIStyle.HEADING_FONT);
        sectionLabel.setForeground(UIStyle.TEXT);
        contentPanel.add(sectionLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Phone", "DOB", "Address", "Notes"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(patientTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(UIStyle.CARD);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(UIStyle.CARD);

        searchField = new JTextField(20);
        UIStyle.styleTextField(searchField);

        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("Reset");
        UIStyle.styleSecondaryButton(searchButton);
        UIStyle.styleSecondaryButton(resetButton);

        searchButton.addActionListener(e -> searchPatients());
        resetButton.addActionListener(e -> loadPatients(patientService.getPatients()));

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(UIStyle.CARD);

        if (editable) {
            JButton addButton = new JButton("Add Patient");
            JButton editButton = new JButton("Edit Selected");
            JButton deleteButton = new JButton("Delete Selected");

            UIStyle.styleButton(addButton);
            UIStyle.styleSecondaryButton(editButton);
            UIStyle.styleDangerButton(deleteButton);

            addButton.addActionListener(e -> addPatient());
            editButton.addActionListener(e -> editPatient());
            deleteButton.addActionListener(e -> deletePatient());

            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
        }

        bottomPanel.add(searchPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadPatients(List<Patient> patients) {
        tableModel.setRowCount(0);

        for (Patient patient : patients) {
            tableModel.addRow(new Object[]{
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhone(),
                patient.getDateOfBirth(),
                patient.getAddress(),
                patient.getNotes()
            });
        }
    }

    private void searchPatients() {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            loadPatients(patientService.getPatients());
        } else {
            loadPatients(patientService.searchPatients(query));
        }
    }

    private void addPatient() {
        PatientFormDialog dialog = new PatientFormDialog(this, "Add Patient", null);
        dialog.setVisible(true);

        Patient patient = dialog.getResult();

        if (patient != null) {
            boolean success = patientService.addPatient(patient);

            if (success) {
                loadPatients(patientService.getPatients());
                JOptionPane.showMessageDialog(this, "Patient added successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Could not add patient. Check the database schema and console errors.");
            }
        }
    }

    private void editPatient() {
        int selectedRow = patientTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient first.");
            return;
        }

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);
        Patient existing = patientService.findPatientById(patientId);

        if (existing == null) {
            JOptionPane.showMessageDialog(this, "Could not find the selected patient.");
            return;
        }

        PatientFormDialog dialog = new PatientFormDialog(this, "Edit Patient", existing);
        dialog.setVisible(true);

        Patient updated = dialog.getResult();

        if (updated != null) {
            updated.setId(patientId);

            if (patientService.editPatient(updated)) {
                loadPatients(patientService.getPatients());
                JOptionPane.showMessageDialog(this, "Patient updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Could not update patient.");
            }
        }
    }

    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient first.");
            return;
        }

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);

        if (patientService.deletePatient(patientId)) {
            loadPatients(patientService.getPatients());
            JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Could not delete patient.");
        }
    }
}