package com.beardream.service;

import com.beardream.Utils.TextUtil;
import com.beardream.dao.ArticleMapper;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.DishMapper;
import com.beardream.model.Business;
import com.beardream.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/6/13.
 */
@Service
public class SearchService {

    @Autowired
    private DishMapper mDishMapper;

    @Autowired
    private BusinessMapper mBusinessMapper;

    @Autowired
    private ArticleMapper mArticleMapper;

    public Map search(String key){

        Map<String, List> resultMap = new HashMap();

        // 如果是根据空结果搜索则是推荐的结果
        if (!TextUtil.isEmpty(key)){
            // 1、先查询商家
            List<Business> businessList = mBusinessMapper.findRecommendList();

            // 2、再查询菜品
            List<Dish> dishList = mDishMapper.findRecommendList();

            // 3、最后查询文章
            // TODO

            resultMap.put("business", businessList);
            resultMap.put("dish", dishList);
            return resultMap;
        }

        // 根据相关结果查询
        // 1、先查询商家
        List<Business> businessList = mBusinessMapper.findBusinessFuzzyList(key);

        // 2、再查询菜品
        List<Dish> dishList = mDishMapper.findDishFuzzyList(key);

        // 3、最后查询文章
        // TODO

        resultMap.put("business", businessList);
        resultMap.put("dish", dishList);
        return resultMap;
    }
}
