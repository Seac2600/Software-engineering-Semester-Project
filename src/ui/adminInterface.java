package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.User;
import services.adminMangment;

public class adminInterface extends JFrame {
    private adminMangment adminService;
    private DefaultTableModel tableModel;
    private JTable userTable;

    public adminInterface(adminMangment adminService) {
        this.adminService = adminService;
        buildUI();
        loadUsersIntoTable();
    }

    private void buildUI() {
        setTitle("Dental Office System - Admin Dashboard");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(UIStyle.BACKGROUND);
        setLayout(new BorderLayout(18, 18));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIStyle.BACKGROUND);
        topPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 0, 18));

        JLabel titleLabel = new JLabel("Admin Dashboard");
        UIStyle.styleLabel(titleLabel, UIStyle.TITLE_FONT, UIStyle.TEXT);

        JLabel subtitleLabel = new JLabel("Manage staff accounts, view user data, and update records.");
        UIStyle.styleLabel(subtitleLabel, UIStyle.BODY_FONT, UIStyle.SUBTLE);

        JPanel titleWrap = new JPanel(new BorderLayout());
        titleWrap.setBackground(UIStyle.BACKGROUND);
        titleWrap.add(titleLabel, BorderLayout.NORTH);
        titleWrap.add(subtitleLabel, BorderLayout.SOUTH);

        topPanel.add(titleWrap, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerCard = new JPanel(new BorderLayout(12, 12));
        UIStyle.styleCard(centerCard);
        centerCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 18, 18, 18),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIStyle.BORDER),
                        BorderFactory.createEmptyBorder(16, 16, 16, 16)
                )
        ));

        JLabel sectionLabel = new JLabel("System Users", SwingConstants.LEFT);
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        sectionLabel.setForeground(UIStyle.TEXT);
        centerCard.add(sectionLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setRowHeight(28);
        userTable.setFont(UIStyle.BODY_FONT);
        userTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        userTable.getTableHeader().setReorderingAllowed(false);
        userTable.setGridColor(UIStyle.BORDER);
        userTable.setShowVerticalLines(false);
        userTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(820, 320));
        centerCard.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(UIStyle.CARD);

        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("Edit Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Logout");

        UIStyle.styleButton(addButton);
        UIStyle.styleSecondaryButton(editButton);
        UIStyle.styleDangerButton(deleteButton);
        UIStyle.styleSecondaryButton(refreshButton);
        UIStyle.styleSecondaryButton(closeButton);

        addButton.addActionListener(e -> addUser());
        editButton.addActionListener(e -> editSelectedUser());
        deleteButton.addActionListener(e -> deleteSelectedUser());
        refreshButton.addActionListener(e -> loadUsersIntoTable());
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        centerCard.add(buttonPanel, BorderLayout.SOUTH);
        add(centerCard, BorderLayout.CENTER);
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
        if (existingUser == null) {
            JOptionPane.showMessageDialog(this, "Could not find that user.");
            return;
        }

        UserFormDialog dialog = new UserFormDialog(this, "Edit User", existingUser, adminService.getNextUserId());
        dialog.setVisible(true);
        User updatedUser = dialog.getUserResult();

        if (updatedUser != null) {
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
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this user?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (adminService.deleteUser(userId)) {
                loadUsersIntoTable();
                JOptionPane.showMessageDialog(this, "User deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Could not delete the user.");
            }
        }
    }
}