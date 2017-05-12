package com.beardream.service;

import com.beardream.dao.UserCollectionMapper;
import com.beardream.model.Dish;
import com.beardream.model.Log;
import com.beardream.model.Nutrition;
import com.beardream.model.UserCollection;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/10.
 */
@Component
@Service
public class CollectionService {

    @Autowired
    public UserCollectionMapper mUserCollectionMapper;


    //添加收藏
    public String add(UserCollection userCollection) {
        int result;
        userCollection.setAddTime(new Date());
        result = mUserCollectionMapper.insertSelective(userCollection);
        if (result > 0) {
            return "收藏成功";
        } else {
            return "收藏失败";
        }
    }

    /*删除收藏
    public int deleteCollection(UserCollection userCollection) {
        return mUserCollectionMapper.deleteByPrimaryKey(userCollection.getCollectionId());
    }
}*/
    public String delete(UserCollection userCollection) {
        int result;
        result = mUserCollectionMapper.deleteByPrimaryKey(userCollection.getCollectionId());
        if (result > 0) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }
}
