
import models.User;
import models.roles;
import services.adminMangment;
import services.loginLogic;
import ui.adminInterface;
import ui.loginInterface;
import data.employeeRecords;

public class main {

    public static void main(String[] args) 
    {
        roles adminRole = new roles(1, "Admin");
        User user = new User(1, "Alice","Johnson", "alice@example.com", "password123", adminRole);

        System.out.println(user.getFirstName() + " " + user.getLastName() + " has role: " + user.getRole().getName());
    }
}
