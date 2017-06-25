package com.beardream.dao;

import com.beardream.model.Business;
import com.beardream.model.BusinessDishTag;
import com.beardream.model.DishBusiness;
import com.beardream.model.UserArticle;
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

    // 根据用户信息获取商家拥有的菜品信息
    List<DishBusiness> findBusinessDishBySelective(@Param("dishNutritionStatus") Integer dishNutritionStatus, @Param("businessId") Integer businessId);

    // 根据用户信息获取商家拥有的所有菜品信息
    List<DishBusiness> findBusinessAllDishBySelective(@Param("dishNutritionStatus") Integer dishNutritionStatus, @Param("businessId") Integer businessId);

    // 获取指定商家id的具体菜品信息
    List<DishBusiness> findBusinessDishInfoBySelective(@Param("dishNutritionStatus") Integer dishNutritionStatus, @Param("businessId") Integer businessId);

    // 查询用户到商家的距离，单位是米
    Business findDistanceBySelective(@Param("latitude") Double latitude, @Param("longtitude") Double longtitude, @Param("businessId") Integer businessId);

    // 推荐商家查询
    List<Business> findRecommendList();

    // 模糊查询商家
    List<Business> findBusinessFuzzyList(@Param("name") String name);

    // 根据菜品id查询商家
    List<Business> findDishBusiness(@Param("dishId") Integer dishId);
}