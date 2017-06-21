package com.beardream.service;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.dao.EvaluateMapper;
import com.beardream.dao.UserCollectionMapper;
import com.beardream.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
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
    public Result add(UserCollection userCollection, HttpSession session) {
        int result;
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        userCollection.setUserId(user.getUserId());
        List<UserCollection> u = mUserCollectionMapper.findBySelective(userCollection);
        if (u.size() > 0)
            return ResultUtil.error(-1, "添加失败，收藏已存在");
        userCollection.setAddTime(new Date());
        userCollection.setUserId(user.getUserId());
        result = mUserCollectionMapper.insertSelective(userCollection);
        if (result > 0)
            return ResultUtil.success("添加成功");
        else
            return ResultUtil.error(-1, "添加失败");
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

    public UserArticle queryArticleCollect(UserArticle userArticle){
        // 根据articleid和用户id查询该用户是否收藏过文章
        UserCollection userCollection = new UserCollection(userArticle.getUserId(), userArticle.getArticleId());
        UserCollection articles = mUserCollectionMapper.findByUserObjId(userCollection);
        if (articles != null){
            userArticle.setCollectionId(articles.getCollectionId());
            return userArticle;
        }else {
            return userArticle;
        }

    }

    public List getCollectionList(UserCollection userCollection, int pageNum, int pageSize) throws UnsupportedEncodingException {
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
                    collection.setArticleUsername(MimeUtility.decodeText(collection.getArticleUsername()));
                }
                return articleCollection;
            default:
                List<UserCollection> defaultCollection =mUserCollectionMapper.findJoinDishBySelective(userCollection);
                return defaultCollection;
        }
    }
}
