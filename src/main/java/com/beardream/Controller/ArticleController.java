package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.ArticleMapper;
import com.beardream.model.*;
import com.beardream.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Created by soft01 on 2017/5/19.
 */
@RestController
@RequestMapping("/api/mobile/article")
@Api(value = "图文服务",description = "提供RESTful风格API的图文的增删改查服务")
public class ArticleController {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleService articleService;

    @ApiOperation("分页获取用户图文")
    @GetMapping
    public Result getPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false)  int pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false)  int pageSize,
                          UserArticle userArticle){
        Map resultPage = articleService.getPage(userArticle,pageNum,pageSize);
        List<UserArticle> userArticles = articleService.splitRecImages(resultPage);
        if (userArticles.size() != 0){
            return ResultUtil.success(userArticles);
        }
        else {
            return ResultUtil.error(-1,"系统错误");
        }
    }

    @ApiOperation("获取一篇文章的详情")
    @GetMapping("/get")
    public Result getArticle(UserArticle userArticle){
        if (!TextUtil.isEmpty(userArticle.getArticleId())){
            return ResultUtil.error(-1,"文章ID不能为空");
        }
        if (articleService.get(userArticle)!=null){
            return  ResultUtil.success(articleService.get(userArticle));
        }
        else {
            return  ResultUtil.error(-1,"文章不存在");
        }
    }

}
