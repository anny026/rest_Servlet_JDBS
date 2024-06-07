package shop.model.repository;
import shop.model.bean.User;

import java.sql.*;

public class UserDao {
    Connection connection = null;
    ResultSet resultSet = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;

    public User getByLogin(String login) {
        User user = null;
        try {
        connection = DBConnectionUtil.openConnection();
        statement = connection.createStatement();
                if (connection != null) {
                PreparedStatement pr = connection.prepareStatement("SELECT * FROM users where username=?");
                pr.setString(1, login);
                resultSet = pr.executeQuery();//return sql result
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            }
            }catch(SQLException e) {
                e.printStackTrace();
            }
            return user;
}

    public Boolean save(String name, String password) {
        connection = DBConnectionUtil.openConnection();
                try {
                PreparedStatement pr = connection.prepareStatement("insert into users " +
                        "(username,password) values"
                        + "('"+name+"', '"+password+"')");
                   pr.executeUpdate();
                 return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
         return false;
    }
}
