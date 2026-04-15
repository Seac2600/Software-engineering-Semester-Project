package data;

import models.User;

public class UserDAOTest {
  public static void main(String[] args) {
    UserDAO dao = new UserDAO();

    User user = dao.login("Edward@mail.com", "Edward123");

    if (user != null) {
        System.out.println("Login successful!");
        System.out.println(user.getFirstName() + " " + user.getLastName());
        System.out.println("Role: " + user.getRole().getRoleName());
    } else {
        System.out.println("Login failed");
    }
}
}