package com.beardream.dao;

import com.beardream.model.Number;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    // 清楚已经过期的号码
    int updateExpiredBySelective(@Param("addTime") Date addTime);
}