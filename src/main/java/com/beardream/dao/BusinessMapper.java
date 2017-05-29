package com.beardream.dao;

import com.beardream.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BusinessMapper {
    int deleteByPrimaryKey(Integer businessId);

    int insert(Business record);

    int insertSelective(Business record);

    Business selectByPrimaryKey(Integer businessId);

    List<Business> findBySelective(Business business);

    int updateByPrimaryKeySelective(Business record);

    int updateByPrimaryKeyWithBLOBs(Business record);

    int updateByPrimaryKey(Business record);

    //商家表 左连接菜品表 和 标签表  查询出商家信息和商家的菜品信息
    List<BusinessDishTag> findBusinessDishTagBySelective (BusinessDishTag businessDishTag);

    // 获取指定商家的菜品信息
    List<DishBusiness> findBusinessDishBySelective(@Param("dishNutritionStatus") Integer dishNutritionStatus, @Param("businessId") Integer businessId);

    // 查询用户到商家的距离，单位是米
    Business findDistanceBySelective(@Param("latitude") Double latitude, @Param("longtitude") Double longtitude, @Param("businessId") Integer businessId);
}