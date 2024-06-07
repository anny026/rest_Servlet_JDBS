package service;

import shop.model.bean.User;
import shop.model.repository.UserDao;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDao();

    /**
     * New user registration.
     *
     * @param name     Username.
     * @param password Password.
     * @return Message about successful registration or the reason for its impossibility.
     */
    public String registration(String name, String password) {
        User findUser = userDao.getByLogin(name);
        System.out.println("Регистрация");
        if(findUser==null) {
            userDao.save( name,  password);
            return "life is beautiful";
        } else {
        System.out.println("Логин существует");
        return "this login is not available";}
    }

    public User findByLogin(String login){
        return userDao.getByLogin(login);
    }

}
