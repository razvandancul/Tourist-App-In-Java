package app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String url = "YOUR-DB-URL";
    private static final String user = "YOUR-DB-USER";
    private static final String password = "YOUR-DB-PASSWORD";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error on connection: " + e.getMessage());
            return null;
        }
    }
}
