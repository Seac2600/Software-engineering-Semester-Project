
import data.UserDAO;
import javax.swing.SwingUtilities;
import services.adminMangment;
import services.appointmentManagement;
import services.loginLogic;
import services.patientManagement;
import ui.loginInterface;

public class main {
    public static void main(String[] args) {
        adminMangment adminService = new adminMangment();
        loginLogic loginService = new loginLogic(new UserDAO());
        patientManagement patientService = new patientManagement();
        appointmentManagement appointmentService = new appointmentManagement();

        SwingUtilities.invokeLater(() -> {
            loginInterface loginUI = new loginInterface(loginService, adminService, patientService, appointmentService);
            loginUI.setVisible(true);
        });
    }
}
