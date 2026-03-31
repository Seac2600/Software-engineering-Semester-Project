
import data.employeeRecords;
import java.util.List;
import javax.swing.SwingUtilities;
import models.User;
import services.adminMangment;
import services.loginLogic;
import ui.loginInterface;


public class main {

    public static void main(String[] args) {
        List<User> users = employeeRecords.getDefaultUsers();

        adminMangment adminService = new adminMangment(users);
        loginLogic loginService = new loginLogic(users);

        SwingUtilities.invokeLater(() -> {
            loginInterface loginUI = new loginInterface(loginService, adminService);
            loginUI.setVisible(true);
        });
    }
}
