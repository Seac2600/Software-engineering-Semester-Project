package services;

import java.util.List;
import models.User;

public class loginLogic {
    private List<User> users;

    public loginLogic(List<User> users) {
        this.users = users;
    }

    public User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
