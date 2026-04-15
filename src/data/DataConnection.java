package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/dental_office_db";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "Compass2600";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
}
