package data;

import java.util.ArrayList;
import java.util.List;
import models.User;
import models.roles;

public class employeeRecords {

    public static List<User> getDefaultUsers() {
        List<User> users = new ArrayList<>();

        roles adminRole = new roles(1, "Admin");
        roles dentistRole = new roles(2, "Dentist");
        roles receptionistRole = new roles(3, "Receptionist");

        users.add(new User(1, "Edward", "Hernandez", "Edward@mail.com", "Edward123", adminRole));
        users.add(new User(2, "Adam", "canedo", "Adam@mail.com", "Adam456", dentistRole));
        users.add(new User(3, "Elisha", "Talavera", "Elisha@mail.com", "Elisha789", receptionistRole));

        return users;
    }
}