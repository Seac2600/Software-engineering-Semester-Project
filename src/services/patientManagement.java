package services;

import data.PatientDAO;
import java.util.List;
import models.Patient;

public class patientManagement {
    private final PatientDAO patientDAO;

    public patientManagement() {
        this.patientDAO = new PatientDAO();
    }

    public patientManagement(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public List<Patient> getPatients() {
        return patientDAO.getAllPatients();
    }

    public boolean addPatient(Patient patient) {
        return patientDAO.addPatient(patient);
    }

    public boolean editPatient(Patient patient) {
        return patientDAO.editPatient(patient);
    }

    public boolean deletePatient(int id) {
        return patientDAO.deletePatient(id);
    }

    public Patient findPatientById(int id) {
        return patientDAO.findPatientById(id);
    }

    public List<Patient> searchPatients(String query) {
        return patientDAO.searchPatients(query);
    }
}