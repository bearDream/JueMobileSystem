package com.beardream.dao;

import com.beardream.model.Number;

public interface NumberMapper {
    int deleteByPrimaryKey(Integer numId);

    int insert(Number record);

    int insertSelective(Number record);

    Number selectByPrimaryKey(Integer numId);

    int updateByPrimaryKeySelective(Number record);

    int updateByPrimaryKey(Number record);
}