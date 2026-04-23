package services;

import data.AppointmentDAO;
import java.util.List;
import models.Appointment;

public class appointmentManagement {
    private final AppointmentDAO appointmentDAO;

    public appointmentManagement() {
        this.appointmentDAO = new AppointmentDAO();
    }

    public appointmentManagement(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public List<Appointment> getAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    public boolean addAppointment(Appointment appointment) {
        return appointmentDAO.addAppointment(appointment);
    }

    public boolean editAppointment(Appointment appointment) {
        return appointmentDAO.editAppointment(appointment);
    }

    public boolean deleteAppointment(int id) {
        return appointmentDAO.deleteAppointment(id);
    }

    public Appointment findAppointmentById(int id) {
        return appointmentDAO.findAppointmentById(id);
    }

    public boolean hasAppointmentConflict(Appointment appointment) {
        return appointmentDAO.hasAppointmentConflict(appointment);
    }
}