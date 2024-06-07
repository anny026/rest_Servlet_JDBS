package service;

import shop.model.bean.Good;

import java.util.Map;

public interface GoodService {
    public Map<Integer, Good> findAllGoods();
    public Integer findPriceByKey(Integer key);

    public Long findIdByKey(Integer key);

    public Good findIdBy(Long i);
}
