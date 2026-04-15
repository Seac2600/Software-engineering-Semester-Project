package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.User;
import services.adminMangment;
import services.appointmentManagement;
import services.patientManagement;

public class adminInterface extends BaseDashboard {
    private final adminMangment adminService;
    private final patientManagement patientService;
    private final appointmentManagement appointmentService;
    private DefaultTableModel tableModel;
    private JTable userTable;

    public adminInterface(adminMangment adminService, patientManagement patientService, appointmentManagement appointmentService) {
        this.adminService = adminService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        buildBase("Admin Dashboard", "Manage staff accounts, patients, and appointments.");
        buildUI();
        loadUsersIntoTable();
    }

    private void buildUI() {
        JPanel wrapper = new JPanel(new BorderLayout(12, 12));
        wrapper.setBackground(UIStyle.CARD);

        JLabel sectionLabel = new JLabel("Staff Accounts", SwingConstants.LEFT);
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionLabel.setForeground(UIStyle.TEXT);
        wrapper.add(sectionLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(820, 320));
        wrapper.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(UIStyle.CARD);

        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("Edit Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton refreshButton = new JButton("Refresh");
        JButton patientsButton = new JButton("Open Patients");
        JButton appointmentsButton = new JButton("Open Appointments");

        UIStyle.styleButton(addButton);
        UIStyle.styleSecondaryButton(editButton);
        UIStyle.styleDangerButton(deleteButton);
        UIStyle.styleSecondaryButton(refreshButton);
        UIStyle.styleSuccessButton(patientsButton);
        UIStyle.styleSuccessButton(appointmentsButton);

        addButton.addActionListener(e -> addUser());
        editButton.addActionListener(e -> editSelectedUser());
        deleteButton.addActionListener(e -> deleteSelectedUser());
        refreshButton.addActionListener(e -> loadUsersIntoTable());

        patientsButton.addActionListener(e -> {
            patientInterface patientUI = new patientInterface(
                patientService,
                true,
                "Patient Management",
                "Create, update, and organize patient records.",
                this
            );
            openChildWindow(patientUI);
        });

        appointmentsButton.addActionListener(e -> {
            appointmentInterface appointmentUI = new appointmentInterface(
                appointmentService,
                patientService,
                adminService,
                true,
                "Appointment Management",
                "Schedule and manage office appointments.",
                this
            );
            openChildWindow(appointmentUI);
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(patientsButton);
        buttonPanel.add(appointmentsButton);

        wrapper.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.add(wrapper, BorderLayout.CENTER);
    }

    private void loadUsersIntoTable() {
        tableModel.setRowCount(0);
        List<User> users = adminService.getUsers();

        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getRoleName()
            });
        }
    }

    private void addUser() {
        UserFormDialog dialog = new UserFormDialog(this, "Add User", null, adminService.getNextUserId());
        dialog.setVisible(true);

        User newUser = dialog.getUserResult();

        if (newUser != null) {
            if (adminService.findUserByEmail(newUser.getEmail()) != null) {
                JOptionPane.showMessageDialog(this, "That email is already in use.");
                return;
            }

            adminService.addUser(newUser);
            loadUsersIntoTable();
            JOptionPane.showMessageDialog(this, "User added successfully.");
        }
    }

    private void editSelectedUser() {
        int selectedRow = userTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        User existingUser = adminService.findUserById(userId);

        UserFormDialog dialog = new UserFormDialog(this, "Edit User", existingUser, adminService.getNextUserId());
        dialog.setVisible(true);

        User updatedUser = dialog.getUserResult();

        if (updatedUser != null) {
            updatedUser.setId(userId);

            User emailOwner = adminService.findUserByEmail(updatedUser.getEmail());
            if (emailOwner != null && emailOwner.getId() != updatedUser.getId()) {
                JOptionPane.showMessageDialog(this, "That email is already in use by another user.");
                return;
            }

            adminService.editUser(updatedUser);
            loadUsersIntoTable();
            JOptionPane.showMessageDialog(this, "User updated successfully.");
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        if (adminService.deleteUser(userId)) {
            loadUsersIntoTable();
            JOptionPane.showMessageDialog(this, "User deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Could not delete user.");
        }
    }
}