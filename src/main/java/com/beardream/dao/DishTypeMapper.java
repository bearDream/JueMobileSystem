package com.beardream.dao;

import com.beardream.model.Business;
import com.beardream.model.DishType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper

public interface DishTypeMapper {
    int deleteByPrimaryKey(Integer dishtypeId);

    int insert(DishType record);

    int insertSelective(DishType record);

    DishType selectByPrimaryKey(Integer dishtypeId);

    List<DishType> findBySelective(DishType dishType);


    int updateByPrimaryKeySelective(DishType record);

    int updateByPrimaryKey(DishType record);
}