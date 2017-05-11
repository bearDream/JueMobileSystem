package com.beardream.dao;

import com.beardream.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User selectByPrimaryKey(Integer userId);

    List<User> findAll();

    User findByMobile(String mobile);

    List<User> findSelective(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int insert(User record);

    int insertSelective(User record);

    int deleteByPrimaryKey(Integer userId);
}