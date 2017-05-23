package com.beardream.dao;

import com.beardream.model.Number;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumberMapper {
    int deleteByPrimaryKey(Integer numId);

    int insert(Number record);

    int insertSelective(Number record);

    Number selectByPrimaryKey(Integer numId);

    List<Number> findBySelective(Number number);

    List<Number> findCurrentNumListBySelective(Number number);

    int updateByPrimaryKeySelective(Number record);

    int updateByPrimaryKey(Number record);
}