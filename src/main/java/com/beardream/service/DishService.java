package com.beardream.service;

import com.beardream.dao.DishMapper;
import com.beardream.model.Dish;
import com.beardream.model.Nutrition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/8.
            */
@Component
@Service
public class DishService {

    @Autowired
    public DishMapper dishMapper;

    public List find(Dish dish){
        System.out.println(dishMapper.selectByPrimaryKey(1));
        List<Dish> dishList = dishMapper.findBySelective(dish);
        return dishList;
    }

    public String post(Dish dish){
        int result;
        dish.setAddTime(new Date());
        result = dishMapper.insertSelective(dish);
        if (result>0){
            return "添加成功";
        }else{
            return "添加失败";
        }
    }

    public String delete(Dish dish){
        int result;
        result = dishMapper.deleteByPrimaryKey(dish.getDishId());
        if (result > 0) {
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    public String put(Dish dish){
        int result;
        System.out.println(dish.getDishId());
        result = dishMapper.updateByPrimaryKeySelective(dish);
        if (result > 0) {
            return "更新成功";
        }else {
            return "更新失败";
        }
    }

    public Map getPage(Dish dish, int pageNum, int pageSize){
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("d.add_time asc");
        List<Dish> dishs =dishMapper.findBySelective(new Dish());
        PageInfo page = new PageInfo(dishs);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("list",page.getList());
        return map;
    }

}
