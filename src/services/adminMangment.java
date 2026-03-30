package services;
import models.User;


import java.util.List;
import java.util.ArrayList;


public class adminMangment {
    private List<User> users = new ArrayList<>();
    
    public void addUser(User user)
    {
        users.add(user);
    }
    public boolean deleteUser(int Id) {
       try {
            if (users.removeIf(user -> user.getId() == Id)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    
    }
    public boolean editUser(User user) {
        try{
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId() == user.getId()) {
                    users.set(i, user);
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
}
