package data;

import java.util.ArrayList;
import java.util.List;
import models.User;
import models.Role;

/*
    do not use this unless you are testing the UI without a database connection. 
    This class provides hardcoded user data for testing purposes. 
    It simulates a simple in-memory data source for users,
     allowing you to test the login functionality and other user-related features without needing to connect to an actual database.
*/

public class MockemployeeRecords {

    public static List<User> getDefaultUsers() {
        List<User> users = new ArrayList<>();

        Role adminRole = new Role(1, "Admin");
        Role dentistRole = new Role(2, "Dentist");
        Role receptionistRole = new Role(3, "Receptionist");

        users.add(new User(1, "Edward", "Hernandez", "Edward@mail.com", "Edward123", adminRole));
        users.add(new User(2, "Adam", "canedo", "Adam@mail.com", "Adam456", dentistRole));
        users.add(new User(3, "Elisha", "Talavera", "Elisha@mail.com", "Elisha789", receptionistRole));

        return users;
    }
}