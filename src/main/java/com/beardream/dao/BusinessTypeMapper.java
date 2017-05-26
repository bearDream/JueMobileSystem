package com.beardream.dao;

import com.beardream.model.BusinessBusinessTypeTag;
import com.beardream.model.BusinessDishTag;
import com.beardream.model.BusinessType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BusinessTypeMapper {
    int deleteByPrimaryKey(Integer businessTypeId);

    int insert(BusinessType record);

    int insertSelective(BusinessType record);

    BusinessType selectByPrimaryKey(Integer businessTypeId);

    List<BusinessType> findBySelective(BusinessType businessType);

    int updateByPrimaryKeySelective(BusinessType record);

    int updateByPrimaryKeyWithBLOBs(BusinessType record);

    int updateByPrimaryKey(BusinessType record);

    //连接商家、商家分类、标签
    List<BusinessBusinessTypeTag> findBusinessBusinessTypeTagBySelective (BusinessBusinessTypeTag businessBusinessTypeTag);
}