package ui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import services.adminMangment;
import services.appointmentManagement;
import services.patientManagement;

public class dentistInterface extends BaseDashboard {
    private final patientManagement patientService;
    private final appointmentManagement appointmentService;
    private final adminMangment adminService;

    public dentistInterface(patientManagement patientService, appointmentManagement appointmentService, adminMangment adminService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.adminService = adminService;

        buildBase("Dentist Dashboard", "View assigned records and upcoming appointments.");
        buildUI();
    }

    private void buildUI() {
        JPanel grid = new JPanel(new GridLayout(1, 2, 16, 16));
        grid.setBackground(UIStyle.CARD);

        JButton patientsButton = new JButton("View Patients");
        JButton appointmentsButton = new JButton("View Appointments");

        UIStyle.styleSecondaryButton(patientsButton);
        UIStyle.styleSecondaryButton(appointmentsButton);

        patientsButton.addActionListener(e -> {
            patientInterface patientUI = new patientInterface(
                patientService,
                false,
                "Patient Records",
                "View patient information and treatment notes."
            );
            openChildWindow(patientUI);
        });

        appointmentsButton.addActionListener(e -> {
            appointmentInterface appointmentUI = new appointmentInterface(
                appointmentService,
                patientService,
                adminService,
                false,
                "Appointment Schedule",
                "View scheduled patient visits."
            );
            openChildWindow(appointmentUI);
        });

        grid.add(patientsButton);
        grid.add(appointmentsButton);

        contentPanel.add(grid);
    }
}