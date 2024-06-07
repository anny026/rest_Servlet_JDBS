package service;

import shop.model.bean.Good;
import shop.model.bean.Order;
import shop.model.bean.OrderGood;
import shop.model.bean.User;

import java.util.Map;

public interface OrderGoodService {
    public void addOrderGood(Good e, User user, Order order, Integer key);

    public Map<Integer, OrderGood> findOrderedGoods(Order order) ;
}
