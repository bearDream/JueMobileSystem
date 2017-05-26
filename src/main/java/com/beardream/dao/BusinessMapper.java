package com.beardream.dao;

import com.beardream.model.Business;
import com.beardream.model.BusinessDishTag;
import com.beardream.model.UserArticle;
import org.apache.ibatis.annotations.Mapper;
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

    //连接查询用户和文章
    List<BusinessDishTag> findBusinessDishTagBySelective (BusinessDishTag businessDishTag);
}