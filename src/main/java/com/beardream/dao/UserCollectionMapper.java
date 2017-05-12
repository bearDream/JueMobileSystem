package com.beardream.dao;

import com.beardream.model.UserCollection;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionMapper {
    int deleteByPrimaryKey(Integer collectionId);

    int insert(UserCollection record);

    int insertSelective(UserCollection record);

    UserCollection selectByPrimaryKey(Integer collectionId);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);
}