package services;

import data.PatientChartEntryDAO;
import java.util.List;
import models.PatientChartEntry;

public class patientChartManagement {
    private final PatientChartEntryDAO chartDAO;

    public patientChartManagement() {
        this.chartDAO = new PatientChartEntryDAO();
    }

    public List<PatientChartEntry> getEntriesByPatientId(int patientId) {
        return chartDAO.getEntriesByPatientId(patientId);
    }

    // Method to get a single entry by ID - used for file operations
    public PatientChartEntry getEntryById(int id) {
        return chartDAO.getEntryById(id);
    }

    public boolean addEntry(PatientChartEntry entry) {
        if (entry.getId() > 0) {
            // This is an update operation
            return chartDAO.updateEntry(entry);
        }
        return chartDAO.addEntry(entry);
    }

    public boolean deleteEntry(int id) {
        return chartDAO.deleteEntry(id);
    }
}