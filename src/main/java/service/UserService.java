package service;

import shop.model.bean.User;

public interface UserService {

    public String registration(String name, String password) ;

    public User findByLogin(String login);
}
