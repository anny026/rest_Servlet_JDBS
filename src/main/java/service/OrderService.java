package service;

import shop.model.bean.Order;
import shop.model.bean.User;

public interface OrderService {
    public void addOrder(User e, Integer price);

    public Order findOrderByIdUser(Long id);
}
