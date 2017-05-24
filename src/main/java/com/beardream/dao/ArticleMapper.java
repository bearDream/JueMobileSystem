package com.beardream.dao;

import com.beardream.model.Article;
import com.beardream.model.UserArticle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleMapper {
    int deleteByPrimaryKey(Integer articleId);

    List<Article> findBySelective(Article article);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    //连接查询用户和文章
    List<UserArticle> findUserArticleBySelective (UserArticle userArticle);
}