package com.beardream.dao;

import com.beardream.model.Nutrition;
import com.beardream.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NutritionMapper {
    int deleteByPrimaryKey(Integer nurtritionId);

    int insert(Nutrition record);

    int insertSelective(Nutrition record);

    List<Nutrition> findBySelective(Nutrition nutrition);

    Nutrition selectByPrimaryKey(Integer nurtritionId);

    int updateByPrimaryKeySelective(Nutrition record);

    int updateByPrimaryKey(Nutrition record);
}