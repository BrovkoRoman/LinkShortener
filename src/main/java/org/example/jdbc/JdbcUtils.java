package org.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static Connection connection;

    public static boolean createConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, "admin", "admin");
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
        connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
