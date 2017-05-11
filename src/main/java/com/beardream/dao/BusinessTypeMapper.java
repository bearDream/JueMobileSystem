package com.beardream.dao;

import com.beardream.model.BusinessType;

public interface BusinessTypeMapper {
    int deleteByPrimaryKey(Integer businessTypeId);

    int insert(BusinessType record);

    int insertSelective(BusinessType record);

    BusinessType selectByPrimaryKey(Integer businessTypeId);

    int updateByPrimaryKeySelective(BusinessType record);

    int updateByPrimaryKeyWithBLOBs(BusinessType record);

    int updateByPrimaryKey(BusinessType record);
}