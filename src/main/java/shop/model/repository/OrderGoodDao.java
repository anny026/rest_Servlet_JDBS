package shop.model.repository;

import shop.model.bean.Good;
import shop.model.bean.Order;
import shop.model.bean.OrderGood;
import shop.model.bean.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The OrderGoodDao class is responsible for managing the saving of OrderGood objects (items added to the cart)
 * in the database.
 * It provides methods for saving a new OrderGood object (an item added to cart) and obtaining a map of the OrderGood
 * objects associated with that object.
 */
public class OrderGoodDao {

    Connection connection = null;
    ResultSet resultSet = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;

    /**
     * Saves a new `OrderGood` object in the database.
     *
     * @return `true` if the `OrderGood` was saved successfully, `false` otherwise
     */
    public boolean save(Good e, User user, Order order, Integer key) {   //  метод для корзины
        boolean flag = false;
        try {
            System.out.println(e);
            String sql = "INSERT INTO order_goods (order_id, good_id) VALUES"
                    + "('"+order.getId()+"', '"+e.getId()+"')";
            connection = DBConnectionUtil.openConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            flag = true;
        }
        catch( SQLException ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * Retrieves all items from a specific order
     *
     */
    public Map<Integer, OrderGood> get(Order order) {
        Map<Integer,OrderGood> data = null;
        OrderGood orderGood = null;
        int i=0;
        try {
            data = new HashMap<>();
            String sql = "SELECT * FROM order_goods WHERE order_id="+order.getId();
            connection = DBConnectionUtil.openConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                orderGood = new OrderGood();
                orderGood.setId(resultSet.getLong("id"));
                orderGood.setOrderId(resultSet.getLong("order_id"));
                orderGood.setGoodId(resultSet.getLong("good_id"));
                data.put(i++,orderGood);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
         return data;
    }
}
