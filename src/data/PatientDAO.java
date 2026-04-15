package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Patient;

public class PatientDAO {

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY id";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                patients.add(mapPatient(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }

    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (first_name, last_name, phone, date_of_birth, address, notes) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            fillPatientStatement(stmt, patient);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editPatient(Patient patient) {
        String sql = "UPDATE patients SET first_name = ?, last_name = ?, phone = ?, date_of_birth = ?, address = ?, notes = ? WHERE id = ?";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            fillPatientStatement(stmt, patient);
            stmt.setInt(7, patient.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Patient findPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapPatient(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Patient> searchPatients(String query) {
        List<Patient> patients = new ArrayList<>();
        String sql = """
            SELECT * FROM patients
            WHERE first_name LIKE ? OR last_name LIKE ? OR phone LIKE ?
            ORDER BY first_name, last_name
        """;

        String like = "%" + query + "%";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                patients.add(mapPatient(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }

    private void fillPatientStatement(PreparedStatement stmt, Patient patient) throws Exception {
        stmt.setString(1, patient.getFirstName());
        stmt.setString(2, patient.getLastName());
        stmt.setString(3, patient.getPhone());

        if (patient.getDateOfBirth() == null || patient.getDateOfBirth().trim().isEmpty()) {
            stmt.setNull(4, java.sql.Types.DATE);
        } else {
            stmt.setDate(4, java.sql.Date.valueOf(patient.getDateOfBirth()));
        }

        stmt.setString(5, patient.getAddress());
        stmt.setString(6, patient.getNotes());
    }

    private Patient mapPatient(ResultSet rs) throws Exception {
        java.sql.Date dob = rs.getDate("date_of_birth");

        return new Patient(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone"),
            dob != null ? dob.toString() : "",
            rs.getString("address"),
            rs.getString("notes")
        );
    }
}