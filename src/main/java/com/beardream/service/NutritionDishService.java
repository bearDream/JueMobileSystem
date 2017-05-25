package com.beardream.service;

import com.beardream.dao.DishMapper;
import com.beardream.model.Dish;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/25.
 */
@Component
@Service
public class NutritionDishService {

    @Autowired
    private DishMapper mDishMapper;

    public Map<String, Object> getdishs(int pageNum, int pageSize, Dish dish){
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<Dish> dishs = mDishMapper.findBySelective(dish);
        PageInfo page = new PageInfo(dishs);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        return map;
    }
}
