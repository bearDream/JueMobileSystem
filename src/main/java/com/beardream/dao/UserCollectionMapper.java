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

    UserCollection findByUserObjId(UserCollection userCollection);

    // 收藏连接菜品查询
    List<UserCollection> findJoinDishBySelective(UserCollection userCollection);

    // 收藏连接商家查询
    List<UserCollection> findJoinBusinessBySelective(UserCollection userCollection);

    // 收藏连接文章查询
    List<UserCollection> findJoinArticleBySelective(UserCollection userCollection);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);
}