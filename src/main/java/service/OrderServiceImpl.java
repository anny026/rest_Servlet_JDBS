package service;

import shop.model.bean.Order;
import shop.model.bean.User;
import shop.model.repository.OrderDao;

public class OrderServiceImpl implements OrderService{

    OrderDao orderDao = new OrderDao();

    public OrderServiceImpl() {
    }

    /**
     * Adds a new order or updates an existing order for the specified user.
     */
    public void addOrder(User e, Integer price){
        if (orderDao.get(e.getId()).getTotalPrice()==0){
            orderDao.save(e,price);
        } else orderDao.update(e,price);
    }

    public Order findOrderByIdUser(Long id){
        return orderDao.get(id);
    }

}
