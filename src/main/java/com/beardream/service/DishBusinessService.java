package com.beardream.service;

import com.beardream.dao.DishBusinessMapper;
import com.beardream.model.Dish;
import com.beardream.model.DoubleDishBusiness;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/27.
 */
@Component
@Service
public class DishBusinessService {

    @Autowired
    private DishBusinessMapper dishBusinessMapper;

    @Autowired
    private DishBusinessService dishBusinessService;

    public DoubleDishBusiness get(DoubleDishBusiness doubleDishBusiness){
        List<DoubleDishBusiness> businessList = dishBusinessMapper.findDoubleDishBusinessBySelective(doubleDishBusiness);

        List<Dish> dishList = new ArrayList<>();
        for (DoubleDishBusiness dishBusiness : businessList) {
            Dish dish = new Dish();
            dish.setDishId(dishBusiness.getDishId());
            dish.setDishName(dishBusiness.getDishName());
            dish.setDishDesc(dishBusiness.getDishDesc());
            dish.setDishImage(dishBusiness.getDishImage());
            //TODO

            dishList.add(dish);
        }
        businessList.get(0).setDishList(dishList);
        return businessList.get(0);
    }

    public Map getPage(DoubleDishBusiness doubleDishBusiness, int pageNum, int pageSize){
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<DoubleDishBusiness> doubleDishBusinesses = dishBusinessMapper.findDoubleDishBusinessBySelective(doubleDishBusiness);
        PageInfo page = new PageInfo(doubleDishBusinesses);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        return map;
    }
}
