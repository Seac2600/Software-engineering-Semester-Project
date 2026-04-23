package ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Patient;
import models.PatientChartEntry;
import services.patientChartManagement;


public class DentistPatientFileInterface extends BaseDashboard {
    private final Patient patient;
    private final patientChartManagement chartService;
    private final BaseDashboard parentWindow;
    private JTable entryTable;
    private DefaultTableModel tableModel;
    

    public DentistPatientFileInterface(Patient patient, patientChartManagement chartService, BaseDashboard parentWindow) {
        this.patient = patient;
        this.chartService = chartService;
        this.parentWindow = parentWindow;

        buildBase(
            "Patient File - " + patient.getFullName(),
            "Dentist-only chart entries, notes, and uploaded files."
        );
        buildUI();
        loadEntries();
    }

    private void buildUI() {
        JLabel header = new JLabel("Patient: " + patient.getFullName());
        header.setFont(UIStyle.HEADING_FONT);
        header.setForeground(UIStyle.TEXT);
        contentPanel.add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Type", "Title", "Description", "Created At", "File Path"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        entryTable = new JTable(tableModel);
        entryTable.setRowHeight(28);
        
        // Hide the ID column (column 0) and File Path column (column 5) since users don't need to see them
        entryTable.getColumnModel().getColumn(0).setMinWidth(0);
        entryTable.getColumnModel().getColumn(0).setMaxWidth(0);
        entryTable.getColumnModel().getColumn(0).setWidth(0);
        entryTable.getColumnModel().getColumn(5).setMinWidth(0);
        entryTable.getColumnModel().getColumn(5).setMaxWidth(0);
        entryTable.getColumnModel().getColumn(5).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(entryTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        buttonPanel.setBackground(UIStyle.CARD);

        JButton addNoteButton = new JButton("Add Note");
        JButton addFileButton = new JButton("Add File/Image");
        JButton openFileButton = new JButton("Open File");
        JButton deleteButton = new JButton("Delete Selected");
        JButton backButton = new JButton("Back");

        UIStyle.styleButton(addNoteButton);
        UIStyle.styleSecondaryButton(addFileButton);
        UIStyle.styleSecondaryButton(openFileButton);
        UIStyle.styleDangerButton(deleteButton);
        UIStyle.styleSecondaryButton(backButton);

        addNoteButton.addActionListener(e -> addNote());
        addFileButton.addActionListener(e -> addFileOrImage());
        openFileButton.addActionListener(e -> openSelectedFile());
        deleteButton.addActionListener(e -> deleteSelectedEntry());
        backButton.addActionListener(e -> goBack());

        buttonPanel.add(addNoteButton);
        buttonPanel.add(addFileButton);
        buttonPanel.add(openFileButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadEntries() {
        tableModel.setRowCount(0);
        List<PatientChartEntry> entries = chartService.getEntriesByPatientId(patient.getId());

        for (PatientChartEntry entry : entries) {
            tableModel.addRow(new Object[]{
                entry.getId(),
                entry.getEntryType(),
                entry.getTitle(),
                entry.getDescription(),
                entry.getCreatedAt(),
                entry.getFilePath()
            });
        }
    }

    private void addNote() {
        String title = JOptionPane.showInputDialog(this, "Enter note title:");
        if (title == null) {
            // User cancelled
            return;
        }
        if (title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            addNote(); // Recursively call to let user try again
            return;
        }

        String description = JOptionPane.showInputDialog(this, "Enter note content:");
        if (description == null) {
            // User cancelled
            return;
        }
        if (description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Note content cannot be empty. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            addNote(); // Recursively call to let user try again
            return;
        }

        PatientChartEntry entry = new PatientChartEntry();
        entry.setPatientId(patient.getId());
        entry.setEntryType("NOTE");
        entry.setTitle(title.trim());
        entry.setDescription(description.trim());
        entry.setFilePath(null);

        if (chartService.addEntry(entry)) {
            loadEntries();
            JOptionPane.showMessageDialog(this, "Note added successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Could not add note. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addFileOrImage() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) return;

        File selectedFile = chooser.getSelectedFile();

        String title = JOptionPane.showInputDialog(this, "Enter file title:");
        if (title == null) {
            // User cancelled
            return;
        }
        if (title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            addFileOrImage(); // Recursively call to let user try again
            return;
        }

        String description = JOptionPane.showInputDialog(this, "Enter description:");
        if (description == null) {
            // User cancelled
            return;
        }
        // Description can be empty for files

        // Copy file to patient_files folder
        String relativePath = copyFileToPatientFiles(selectedFile);
        if (relativePath == null) {
            JOptionPane.showMessageDialog(this, "Could not copy file. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fileType = isImageFile(selectedFile) ? "IMAGE" : "FILE";

        PatientChartEntry entry = new PatientChartEntry();
        entry.setPatientId(patient.getId());
        entry.setEntryType(fileType);
        entry.setTitle(title.trim());
        entry.setDescription(description.trim());
        entry.setFilePath(relativePath); // Store relative path

        if (chartService.addEntry(entry)) {
            loadEntries();
            JOptionPane.showMessageDialog(this, "File added successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Could not add file. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg")
                || name.endsWith(".jpeg") || name.endsWith(".gif");
    }

    private void showImagePreview(File imageFile, String title) {
        try {
            if (!imageFile.exists()) {
                JOptionPane.showMessageDialog(this, "Image file not found.");
                return;
            }

            ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
            if (icon.getImage().getWidth(null) > 600) {
                int newWidth = 600;
                int newHeight = (int) (icon.getImage().getHeight(null) * (600.0 / icon.getImage().getWidth(null)));
                icon = new ImageIcon(icon.getImage().getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH));
            }

            JLabel imageLabel = new JLabel(icon);
            JScrollPane scrollPane = new JScrollPane(imageLabel);
            scrollPane.setPreferredSize(new Dimension(650, 500));

            JOptionPane.showMessageDialog(this, scrollPane, "Image: " + title, JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not display image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String copyFileToPatientFiles(File sourceFile) {
        try {
            // Create patient_files directory if it doesn't exist
            Path patientFilesDir = Paths.get("patient_files");
            if (!Files.exists(patientFilesDir)) {
                Files.createDirectories(patientFilesDir);
            }

            // Generate unique filename to avoid conflicts
            String originalName = sourceFile.getName();
            String extension = "";
            int lastDot = originalName.lastIndexOf('.');
            if (lastDot > 0) {
                extension = originalName.substring(lastDot);
                originalName = originalName.substring(0, lastDot);
            }

            String uniqueName = originalName + "_" + UUID.randomUUID().toString().substring(0, 8) + extension;
            Path destinationPath = patientFilesDir.resolve(uniqueName);

            // Copy the file
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Return relative path
            return "patient_files/" + uniqueName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void openSelectedFile() {
        int selectedRow = entryTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an entry first.");
            return;
        }

        String entryType = (String) tableModel.getValueAt(selectedRow, 1);
        String title = (String) tableModel.getValueAt(selectedRow, 2);
        String relativePath = (String) tableModel.getValueAt(selectedRow, 5);
        if (relativePath == null || relativePath.isBlank()) {
            JOptionPane.showMessageDialog(this, "This entry does not have a file.");
            return;
        }

        try {
            // Convert relative path to absolute path in patient_files folder
            Path filePath = Paths.get(System.getProperty("user.dir")).resolve(relativePath);
            File fileToOpen = filePath.toFile();

            if (!fileToOpen.exists()) {
                JOptionPane.showMessageDialog(this, "File not found: " + relativePath);
                return;
            }

            if (entryType.equals("IMAGE")) {
                showImagePreview(fileToOpen, title);
            } else {
                Desktop.getDesktop().open(fileToOpen);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not open file.");
            e.printStackTrace();
        }
    }

    private void deleteSelectedEntry() {
        int selectedRow = entryTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an entry first.");
            return;
        }

        int entryId = (int) tableModel.getValueAt(selectedRow, 0);
        String relativePath = (String) tableModel.getValueAt(selectedRow, 5);

        // Delete the actual file if it exists
        if (relativePath != null && !relativePath.isBlank()) {
            try {
                Path filePath = Paths.get(System.getProperty("user.dir")).resolve(relativePath);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Log error but don't prevent database deletion
                System.err.println("Could not delete file: " + relativePath);
            }
        }

        if (chartService.deleteEntry(entryId)) {
            loadEntries();
            JOptionPane.showMessageDialog(this, "Entry deleted.");
        } else {
            JOptionPane.showMessageDialog(this, "Could not delete entry.");
        }
    }

    private void goBack() {
        parentWindow.setVisible(true);
        dispose();
    }
}