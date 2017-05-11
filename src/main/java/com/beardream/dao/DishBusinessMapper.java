package com.beardream.dao;

import com.beardream.model.DishBusiness;

public interface DishBusinessMapper {
    int deleteByPrimaryKey(Integer dishBusinessId);

    int insert(DishBusiness record);

    int insertSelective(DishBusiness record);

    DishBusiness selectByPrimaryKey(Integer dishBusinessId);

    int updateByPrimaryKeySelective(DishBusiness record);

    int updateByPrimaryKey(DishBusiness record);
}