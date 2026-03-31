package services;

import java.util.ArrayList;
import java.util.List;
import models.User;

public class adminMangment {
    private List<User> users;

    public adminMangment() {
        this.users = new ArrayList<>();
    }

    public adminMangment(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean deleteUser(int id) {
        try {
            return users.removeIf(user -> user.getId() == id);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editUser(User updatedUser) {
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId() == updatedUser.getId()) {
                    users.set(i, updatedUser);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public int getNextUserId() {
        int maxId = 0;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }
}






