package shop.model.repository;

import java.io.IOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {

    private static Connection connection = null;
    public static Connection openConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                String URL;
                String USERNAME;
                String PASSWORD;
                String driverName;
                final String DB_PROPS_PATH = "application.properties";
                Properties props = new Properties();
                try (InputStream inp = DBConnectionUtil.class.getClassLoader().getResourceAsStream(DB_PROPS_PATH)) {
                    if (inp != null) {
                        props.load(inp);
                        System.out.println("inp != null");
                        inp.close();
                    }
                    driverName = props.getProperty("db.driver");
                    URL = props.getProperty("db.host");
                    USERNAME = props.getProperty("username");
                    PASSWORD = props.getProperty("password");
                    try {
                        Class.forName(driverName);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

//                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/restService", "postgres", "pg88");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return connection;
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
