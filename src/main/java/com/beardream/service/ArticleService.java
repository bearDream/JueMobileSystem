package com.beardream.service;

import com.beardream.dao.ArticleMapper;
import com.beardream.model.UserArticle;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public UserArticle get(UserArticle userArticle){
        return articleMapper.findUserArticleBySelective(userArticle).get(0);
    }

    public Map getPage(UserArticle userArticle, int pageNum, int pageSize){
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("praise desc");
        List<UserArticle> userArticles = articleMapper.findUserArticleBySelective(userArticle);
        PageInfo page = new PageInfo(userArticles);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("list", page.getList());
        return map;
    }

    public List<UserArticle> splitRecImages(Map articleMap){
        List<UserArticle> userArticles = (List<UserArticle>) articleMap.get("list");
        // 分割图片并存在对象的集合中
        for (UserArticle userArticle : userArticles) {
            String recImage = userArticle.getRecImage();
            List<String> articles = new ArrayList<>();
            if (recImage != null){
                String[] recImages = recImage.split(",");
                for (int i = 0; i < recImages.length; i++)
                    articles.add(recImages[i]);
                userArticle.setRecImageList(articles);
            }
        }
        return userArticles;
    }

}
