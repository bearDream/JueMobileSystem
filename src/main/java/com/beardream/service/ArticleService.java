package com.beardream.service;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.ArticleMapper;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.model.UserArticle;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.sun.xml.internal.ws.util.ReadAllStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by soft01 on 2017/5/24.
 */
@Component
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper mArticleMapper;

    public UserArticle get(UserArticle userArticle){
        return mArticleMapper.findUserArticleBySelective(userArticle).get(0);
    }

    public Map getPage(UserArticle userArticle, int pageNum, int pageSize){
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("praise desc");
        List<UserArticle> userArticles = mArticleMapper.findUserArticleBySelective(userArticle);
        PageInfo page = new PageInfo(userArticles);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("list", page.getList());
        return map;
    }

    public Map splitRecImages(Map articleMap) throws UnsupportedEncodingException {
        PageInfo p = (PageInfo) articleMap.get("page");
        List<UserArticle> userArticles = p.getList();

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
            // 转码用户username
            userArticle.setUsername(MimeUtility.decodeText(userArticle.getUsername()));
        }

        p.setList(userArticles);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", p);
        return map;
    }

    // 上传文章
    public Result addArticle(UserArticle userArticle, HttpSession session){
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);

        if (!TextUtil.isEmpty(userArticle.getTitle()))
            return ResultUtil.error(-1,"请检查文章标题是否填写");
        if (!TextUtil.isEmpty(userArticle.getContent()))
            return ResultUtil.error(-1,"请检查文章内容是否填写");
        if (!TextUtil.isEmpty(userArticle.getCoverImage()))
            return ResultUtil.error(-1,"请检查文章封面图是否上传");


        System.out.println("用户id: " + user.getUserId());
        userArticle.setAddTime(new Date());
        userArticle.setUserId(user.getUserId());
        if (mArticleMapper.insertSelective(userArticle) == 1){
            return ResultUtil.success("发表成功");
        }
        return ResultUtil.success("发表失败，请稍候重试");
    }

    public Result update(UserArticle userArticle) {
        if (userArticle.getPraise() == 1){
            int result = mArticleMapper.praiseArticleByPrimaryKey(userArticle);
            if (result == 1){
                return ResultUtil.success("点赞成功");
            }
            return ResultUtil.success("点赞失败");
        }
        return null;
    }
}
