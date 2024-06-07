package shop.model.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import shop.model.bean.Good;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class GoodDaoTest {

    GoodDao goodDao = new GoodDao();

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setup() {
        try {
            Connection connection = DriverManager.getConnection(
                    postgres.getJdbcUrl(),
                    postgres.getUsername(),
                    postgres.getPassword()
            );
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
            goodDao.setConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testGetAll() {
        Map<Integer, Good> goods = goodDao.getAll();
        assertNotNull(goods);
        assertTrue(goods.size() > 0);
    }


}