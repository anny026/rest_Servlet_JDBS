package service;

import shop.model.bean.Good;
import shop.model.repository.GoodDao;

import java.util.Map;

public class GoodServiceImpl implements GoodService{

    GoodDao goodDao = new GoodDao();

    public Integer findPriceByKey(Integer key) {

        return goodDao.getPricebyKey(key);
    }

    public Map<Integer,Good> findAllGoods(){

        return goodDao.getAll();
    }
       public Long findIdByKey(Integer key) {

        return goodDao.getIdbyKey(key);
    }

    public Good findIdBy(Long i) {

        return goodDao.get(Long.valueOf(i));
    }

}
