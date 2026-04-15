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
}
