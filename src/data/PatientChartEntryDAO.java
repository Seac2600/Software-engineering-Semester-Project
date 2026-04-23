package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.PatientChartEntry;

public class PatientChartEntryDAO {

    public List<PatientChartEntry> getEntriesByPatientId(int patientId) {
        List<PatientChartEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM patient_chart_entries WHERE patient_id = ? ORDER BY created_at DESC";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                entries.add(mapEntry(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entries;
    }

    public PatientChartEntry getEntryById(int id) {
        String sql = "SELECT * FROM patient_chart_entries WHERE id = ?";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapEntry(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addEntry(PatientChartEntry entry) {
        String sql = """
            INSERT INTO patient_chart_entries
            (patient_id, entry_type, title, description, file_path)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entry.getPatientId());
            stmt.setString(2, entry.getEntryType());
            stmt.setString(3, entry.getTitle());
            stmt.setString(4, entry.getDescription());
            stmt.setString(5, entry.getFilePath());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEntry(int id) {
        String sql = "DELETE FROM patient_chart_entries WHERE id = ?";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEntry(PatientChartEntry entry) {
        String sql = "UPDATE patient_chart_entries SET title = ?, description = ? WHERE id = ?";

        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entry.getTitle());
            stmt.setString(2, entry.getDescription());
            stmt.setInt(3, entry.getId());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private PatientChartEntry mapEntry(ResultSet rs) throws Exception {
        return new PatientChartEntry(
            rs.getInt("id"),
            rs.getInt("patient_id"),
            rs.getString("entry_type"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("file_path"),
            rs.getTimestamp("created_at").toString()
        );
    }
}