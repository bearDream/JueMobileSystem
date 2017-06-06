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

import javax.servlet.http.HttpSession;
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
    private ArticleService mArticleService;

    @ApiOperation("分页获取用户图文")
    @GetMapping
    public Result getPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false)  int pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false)  int pageSize,
                          UserArticle userArticle){
        Map resultPage = mArticleService.getPage(userArticle,pageNum,pageSize);
        resultPage = mArticleService.splitRecImages(resultPage);
        if (resultPage.get("page") != null){
            return ResultUtil.success(resultPage);
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
        UserArticle userArticle1 = mArticleService.get(userArticle);
        if (userArticle1!=null){
            return  ResultUtil.success(userArticle1);
        }
        else {
            return  ResultUtil.error(-1,"文章不存在");
        }
    }

    @ApiOperation("上传文章接口")
    @PostMapping
    public @ResponseBody Result addArticle(@RequestBody UserArticle userArticle, HttpSession session){
        return  mArticleService.addArticle(userArticle, session);
    }

}
