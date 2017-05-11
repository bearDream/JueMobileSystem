package com.beardream.dao;

import com.beardream.model.Method;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface MethodMapper {
    int deleteByPrimaryKey(Integer methodId);

    int insert(Method record);

    int insertSelective(Method record);

    Method selectByPrimaryKey(Integer methodId);

    List<Method> selectBySelective(Method method);

    int updateByPrimaryKeySelective(Method record);

    int updateByPrimaryKey(Method record);

    int truncate();
}