package services;

import data.UserDAO;
import models.User;

public class loginLogic {
    private final UserDAO userDAO;

    public loginLogic(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User authenticate(String email, String password) {
        return userDAO.login(email, password);
    }

    public boolean resetPassword(String email, String firstName, String lastName, String newPassword) {
        User user = userDAO.findUserByEmail(email);
        if (user == null) {
            return false;
        }
        if (!user.getFirstName().equalsIgnoreCase(firstName.trim()) ||
            !user.getLastName().equalsIgnoreCase(lastName.trim())) {
            return false;
        }
        return userDAO.updatePassword(user.getId(), newPassword);
    }
}
