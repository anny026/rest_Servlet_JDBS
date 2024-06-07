package shop.model.repository;


import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class ContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Connection connection;
        String URL;
        String USERNAME ;
        String PASSWORD ;
        String driverName ;
        final String DB_PROPS_PATH = "application.properties";
        Properties props = new Properties();
        try (InputStream inp = getClass().getClassLoader().getResourceAsStream(DB_PROPS_PATH)) {
            if (inp != null) {
                props.load(inp);
                System.out.println("inp != null");
                inp.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driverName = props.getProperty("db.driver");
        URL = props.getProperty("db.host");
        USERNAME = props.getProperty("username");
        PASSWORD = props.getProperty("password");
        System.out.println(driverName);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement st = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "username VARCHAR(255)," +
                "password VARCHAR(255)" +
                ")")) {
            System.out.println("CREATE TABLE: rows updated - " + st.executeUpdate());


            try (PreparedStatement st2 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS goods (" +
                    "id SERIAL PRIMARY KEY," +
                    "title VARCHAR(255)," +
                    "price INTEGER" +
                    ")")) {
                System.out.println("CREATE TABLE: rows updated - " + st2.executeUpdate());
            }

            try (PreparedStatement st3 = connection.prepareStatement("INSERT INTO goods (title,price) values " +
                    "('" + "coffee" + "', '" + 5 + "')" + ";" +
                    "INSERT INTO goods (title,price) values" + "('" + "lemon" + "', '" + 4 + "')" + ";" +
                    "INSERT INTO goods (title,price) values" + "('" + "tea" + "', '" + 3 + "')" + ";" +
                    "INSERT INTO goods (title,price) values" + "('" + "milk" + "', '" + 2 + "')" + ";" +
                    "INSERT INTO goods (title,price) values" + "('" + "cheese" + "', '" + 6 + "')")) {

                System.out.println("Queries: " + st3.executeUpdate());
            }

            try (PreparedStatement st5 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS order_goods (" +
                    "id SERIAL PRIMARY KEY," +
                    "order_id INTEGER," +
                    "good_id INTEGER" +
                    ")")) {
                System.out.println("CREATE TABLE: rows updated - " + st5.executeUpdate());
            }

            try (PreparedStatement st7 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS user_order (" +
                    "id SERIAL PRIMARY KEY," +
                    "user_id INTEGER," +
                    "total_price INTEGER" +
                    ")")) {
                System.out.println("CREATE TABLE: rows updated - " + st7.executeUpdate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
