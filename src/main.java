
import javax.swing.SwingUtilities;

import data.UserDAO;
import services.adminMangment;
import services.loginLogic;
import ui.loginInterface;


public class main {

    public static void main(String[] args) {
     
        adminMangment adminService = new adminMangment();
        loginLogic loginService = new loginLogic(new UserDAO());

        SwingUtilities.invokeLater(() -> {
            loginInterface loginUI = new loginInterface(loginService, adminService);
            loginUI.setVisible(true);
        });
    }
}
