package models;

public class Role {
    private int id;
    private String rolename;

    public Role() {
    }

    public Role(int id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return rolename;
    }

    public void setRoleName(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return rolename;
    }
}








