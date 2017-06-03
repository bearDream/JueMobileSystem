package com.beardream.service;

import com.beardream.dao.EvaluateMapper;
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
    private UserCollectionMapper mUserCollectionMapper;

    @Autowired
    private EvaluateMapper mEvaluateMapper;


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

    public List getCollectionList(UserCollection userCollection, int pageNum, int pageSize){
        // 1、查询出收藏表基本信息
        List<UserCollection> collectionList =mUserCollectionMapper.findBySelective(userCollection);
        // 2、根据基本信息中的collection_type，business_dish_id连接菜品/商家/文章 进行查询
        switch (userCollection.getCollectionType()){
            case 1:
                List<UserCollection> dishCollection =mUserCollectionMapper.findJoinDishBySelective(userCollection);
                return dishCollection;
            case 2:
                List<UserCollection> businessCollection =mUserCollectionMapper.findJoinBusinessBySelective(userCollection);
                return businessCollection;
            case 3:
                List<UserCollection> articleCollection =mUserCollectionMapper.findJoinArticleBySelective(userCollection);
                // 查找文章的评论数量
                for (UserCollection collection : articleCollection) {
                    collection.setComment(mEvaluateMapper.findArticleEvaluate(collection.getArticleId(), 3).size());
                }
                return articleCollection;
            default:
                List<UserCollection> defaultCollection =mUserCollectionMapper.findJoinDishBySelective(userCollection);
                return defaultCollection;
        }
    }
}
