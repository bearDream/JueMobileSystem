package com.beardream.service;

import com.beardream.dao.ArticleMapper;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.DishMapper;
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

        // 先查询商家
        mBusinessMapper.findBusinessFuzzyList(key);
        return resultMap;
    }
}
