package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Appointment;
import models.Patient;
import models.Role;
import models.User;

public class AppointmentDAO {
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = baseSelect() + " ORDER BY a.appointment_date, a.appointment_time";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                appointments.add(mapAppointment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public boolean addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (patient_id, staff_user_id, appointment_date, appointment_time, reason) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            fillAppointmentStatement(stmt, appointment);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET patient_id = ?, staff_user_id = ?, appointment_date = ?, appointment_time = ?, reason = ? WHERE id = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            fillAppointmentStatement(stmt, appointment);
            stmt.setInt(6, appointment.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Appointment findAppointmentById(int id) {
        String sql = baseSelect() + " WHERE a.id = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapAppointment(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String baseSelect() {
        return """
            SELECT a.id,
                   a.appointment_date,
                   a.appointment_time,
                   a.reason,
                   p.id AS patient_id,
                   p.first_name AS patient_first_name,
                   p.last_name AS patient_last_name,
                   p.phone,
                   p.date_of_birth,
                   p.address,
                   p.notes,
                   u.id AS user_id,
                   u.first_name AS user_first_name,
                   u.last_name AS user_last_name,
                   u.email,
                   u.password,
                   r.id AS role_id,
                   r.role_name
            FROM appointments a
            JOIN patients p ON a.patient_id = p.id
            JOIN users u ON a.staff_user_id = u.id
            JOIN roles r ON u.role_id = r.id
        """;
    }

    private void fillAppointmentStatement(PreparedStatement stmt, Appointment appointment) throws Exception {
    stmt.setInt(1, appointment.getPatient().getId());
    stmt.setInt(2, appointment.getStaffUser().getId());
    stmt.setDate(3, java.sql.Date.valueOf(appointment.getAppointmentDate()));
    stmt.setTime(4, java.sql.Time.valueOf(appointment.getAppointmentTime()));
    stmt.setString(5, appointment.getReason());
}
    private Appointment mapAppointment(ResultSet rs) throws Exception {
        Patient patient = new Patient(
            rs.getInt("patient_id"),
            rs.getString("patient_first_name"),
            rs.getString("patient_last_name"),
            rs.getString("phone"),
            rs.getString("date_of_birth"),
            rs.getString("address"),
            rs.getString("notes")
        );
        Role role = new Role(rs.getInt("role_id"), rs.getString("role_name"));
        User staffUser = new User(
            rs.getInt("user_id"),
            rs.getString("user_first_name"),
            rs.getString("user_last_name"),
            rs.getString("email"),
            rs.getString("password"),
            role
        );
        return new Appointment(
            rs.getInt("id"),
            patient,
            staffUser,
            rs.getString("appointment_date"),
            rs.getString("appointment_time"),
            rs.getString("reason")
        );
    }
    public boolean hasAppointmentConflict(Appointment appointment) {
    String sql = """
        SELECT COUNT(*)
        FROM appointments
        WHERE appointment_date = ?
          AND appointment_time = ?
          AND (staff_user_id = ? OR patient_id = ?)
          AND id <> ?
    """;

    try (Connection conn = DataConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
        stmt.setTime(2, java.sql.Time.valueOf(appointment.getAppointmentTime()));
        stmt.setInt(3, appointment.getStaffUser().getId());
        stmt.setInt(4, appointment.getPatient().getId());
        stmt.setInt(5, appointment.getId());

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

}
