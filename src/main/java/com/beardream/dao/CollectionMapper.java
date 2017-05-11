package com.beardream.dao;

import com.beardream.model.Collection;

public interface CollectionMapper {
    int deleteByPrimaryKey(Integer collectionId);

    int insert(Collection record);

    int insertSelective(Collection record);

    Collection selectByPrimaryKey(Integer collectionId);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);
}