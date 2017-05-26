package com.beardream.dao;

import com.beardream.model.DishBusiness;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DishBusinessMapper {
    int deleteByPrimaryKey(Integer dishBusinessId);

    int insert(DishBusiness record);

    int insertSelective(DishBusiness record);

    DishBusiness selectByPrimaryKey(Integer dishBusinessId);


    int updateByPrimaryKeySelective(DishBusiness record);

    int updateByPrimaryKey(DishBusiness record);
}