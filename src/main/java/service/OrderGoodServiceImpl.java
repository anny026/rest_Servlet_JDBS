package service;

import shop.model.bean.Good;
import shop.model.bean.Order;
import shop.model.bean.OrderGood;
import shop.model.bean.User;
import shop.model.repository.OrderGoodDao;

import java.util.Map;

public class OrderGoodServiceImpl implements OrderGoodService {

    OrderGoodDao orderGoodDao = new OrderGoodDao();

    public void addOrderGood(Good e, User user, Order order, Integer key) {
                orderGoodDao.save(e, user, order, key);
    }

    public Map<Integer, OrderGood> findOrderedGoods(Order order) {
        return orderGoodDao.get(order);
    }
}
