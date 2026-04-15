package ui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import services.adminMangment;
import services.appointmentManagement;
import services.patientManagement;

public class receptionistInterface extends BaseDashboard {
    private final patientManagement patientService;
    private final appointmentManagement appointmentService;
    private final adminMangment adminService;

    public receptionistInterface(patientManagement patientService, appointmentManagement appointmentService, adminMangment adminService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.adminService = adminService;

        buildBase("Receptionist Dashboard", "Manage patient profiles and schedule visits.");
        buildUI();
    }

    private void buildUI() {
        JPanel grid = new JPanel(new GridLayout(1, 2, 16, 16));
        grid.setBackground(UIStyle.CARD);

        JButton patientsButton = new JButton("Manage Patients");
        JButton appointmentsButton = new JButton("Manage Appointments");

        UIStyle.styleButton(patientsButton);
        UIStyle.styleSuccessButton(appointmentsButton);

        patientsButton.addActionListener(e -> {
            patientInterface patientUI = new patientInterface(
                patientService,
                true,
                "Patient Management",
                "Create and update patient profiles.",
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
                "Create and update appointments.",
                this
            );
            openChildWindow(appointmentUI);
        });

        grid.add(patientsButton);
        grid.add(appointmentsButton);

        contentPanel.add(grid);
    }
}