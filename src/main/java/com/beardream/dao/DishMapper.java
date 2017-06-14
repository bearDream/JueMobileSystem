package com.beardream.dao;

import com.beardream.model.Business;
import com.beardream.model.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DishMapper {
    int deleteByPrimaryKey(Integer dishId);

    int insert(Dish record);

    int insertSelective(Dish record);

    List<Dish> findBySelective(Dish dish);

    List<Dish> findDishNutrition(Dish dish);

    Dish selectByPrimaryKey(Integer dishId);

    int updateByPrimaryKeySelective(Dish record);

    int updateByPrimaryKeyWithBLOBs(Dish record);

    int updateByPrimaryKey(Dish record);

    // 推荐菜品查询
    List<Dish> findRecommendList();

    // 模糊查询菜品
    List<Dish> findDishFuzzyList(@Param("dishName") String dishName);
}