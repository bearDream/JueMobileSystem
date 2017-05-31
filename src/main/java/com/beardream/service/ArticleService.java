package com.beardream.service;

import com.beardream.dao.ArticleMapper;
import com.beardream.model.UserArticle;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/24.
 */
@Component
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleService articleService;

    public UserArticle get(UserArticle userArticle){
        return articleMapper.findUserArticleBySelective(userArticle).get(0);
    }

    public Map getPage(UserArticle userArticle, int pageNum, int pageSize){
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("business_id desc");
        List<UserArticle> userArticles = articleMapper.findUserArticleBySelective(new UserArticle());
        PageInfo page = new PageInfo(userArticles);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        return map;
    }

}
