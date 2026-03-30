package models;

public class roles {
    private int id;
    private static String[] roleNames; 

    

    public roles() {
        roleNames = new String[]{"Admin", "Dentist", "Receptionist", "patient"};
    }

    public roles(int id, String name) {
        this.id = id;
        roleNames = new String[]{"Admin", "Dentist", "Receptionist", "patient"};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return roleNames[id];
    }

    public void setRoleName(String name) {
        this.roleNames[id] = name;
    }
}
