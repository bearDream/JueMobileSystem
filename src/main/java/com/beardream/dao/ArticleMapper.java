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

    int insertSelective(UserArticle record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    // 查询所有文章，并且获取文章是否被某用户收藏的信息
    List<UserArticle> findAllArticleInfoBySelective (UserArticle userArticle);

    //连接查询用户和文章
    List<UserArticle> findUserArticleBySelective (UserArticle userArticle);

    //对文章进行点赞
    Integer praiseArticleByPrimaryKey (UserArticle userArticle);
}