package com.beardream.service;

import com.beardream.dao.BusinessMapper;
import com.beardream.dao.DishBusinessMapper;
import com.beardream.dao.DishMapper;
import com.beardream.model.Business;
import com.beardream.model.Dish;
import com.beardream.model.DishBusiness;
import com.beardream.model.DoubleDishBusiness;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.map.HashedMap;
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
    private BusinessMapper mBusinessMapper;

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

    // 获取菜品信息以及商家信息
    public Map getDishInfo(String dishBusinessId) {
        // 1、获取到dishBusinessId字符串，根据“，”分割开来
        String[] dishArray = dishBusinessId.split(",");

        // 2、根据三个dishBusinessId查询到三个菜品信息，装到list中
        List<DishBusiness> dishList = new ArrayList<>();
        for (int i = 0; i < dishArray.length; i++) {
            System.out.println(dishArray[i]);
            dishList.add(dishBusinessMapper.findDishBusinessBySelective(Integer.valueOf(dishArray[i])));
        }
        // 3、再查询出商家信息
        Business business = mBusinessMapper.selectByPrimaryKey(dishList.get(0).getBusinessId());

        // 4、将菜品信息的list和商家信息的bean装到一个map中返回给前端
        Map map = new HashedMap();
        map.put("dishList", dishList);
        map.put("business", business);
        return map;
    }
}
