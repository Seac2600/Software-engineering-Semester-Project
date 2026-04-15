package services;

import data.UserDAO;
import java.util.List;
import models.User;

public class adminMangment {
    private final UserDAO userDAO;

    public adminMangment() {
        this.userDAO = new UserDAO();
    }

    public adminMangment(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }

    public boolean editUser(User updatedUser) {
        return userDAO.editUser(updatedUser);
    }

    public List<User> getUsers() {
        return userDAO.getAllUsers();
    }

    public List<User> getStaffUsers() {
        return userDAO.getStaffUsers();
    }

    public User findUserById(int id) {
        return userDAO.findUserById(id);
    }

    public User findUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    public int getNextUserId() {
        return userDAO.getNextUserId();
    }
}




