package com.beardream.dao;

import com.beardream.model.Dish;
import com.beardream.model.UserCollection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// ..
@Repository
@Mapper
public interface UserCollectionMapper {
    int deleteByPrimaryKey(Integer collectionId);

    int insert(UserCollection record);

    int insertSelective(UserCollection record);

    UserCollection selectByPrimaryKey(Integer collectionId);

    List<UserCollection> findBySelective(UserCollection userCollection);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);
}