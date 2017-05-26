package com.beardream.dao;

import com.beardream.model.Business;
import com.beardream.model.BusinessDishTag;
import com.beardream.model.DishType;
import com.beardream.model.DishTypeBusinessTag;
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

    List<DishTypeBusinessTag> findDishTypeBusinessTagBySelective (DishTypeBusinessTag dishTypeBusinessTag);

    int updateByPrimaryKeySelective(DishType record);

    int updateByPrimaryKey(DishType record);
}