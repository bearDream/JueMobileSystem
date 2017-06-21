package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.ArticleMapper;
import com.beardream.model.*;
import com.beardream.service.ArticleService;
import com.beardream.service.CollectionService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
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
    private ArticleService mArticleService;

    @Autowired
    private CollectionService mCollectionService;

    @ApiOperation("分页获取用户图文")
    @GetMapping
    public Result getPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false)  int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10", required = false)  int pageSize,
                          UserArticle userArticle){
        Map resultPage = mArticleService.getPage(userArticle,pageNum,pageSize);
        try {
            resultPage = mArticleService.splitRecImages(resultPage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultUtil.error(-1,"系统错误");
        }
        if (resultPage.get("page") != null){
            return ResultUtil.success(resultPage);
        }
        else {
            return ResultUtil.error(-1,"系统错误");
        }
    }

    @ApiOperation("获取用户发布的图文")
    @GetMapping("/own")
    public Result getPage(HttpSession session){
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        List<UserArticle> userArticleList = mArticleService.getOwnArticle(user);
        if (userArticleList.size() == 0)
            return ResultUtil.error(-1,"您还没发布任何图文呢");
        return ResultUtil.success(userArticleList);
    }

    @ApiOperation("获取一篇文章的详情")
    @GetMapping("/get")
    public Result getArticle(UserArticle userArticle, HttpSession session){
        if (!TextUtil.isEmpty(userArticle.getArticleId())){
            return ResultUtil.error(-1,"文章ID不能为空");
        }
        UserArticle userArticle1 = mArticleService.get(userArticle);
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        userArticle1.setUserId(user.getUserId());

        userArticle1 = mCollectionService.queryArticleCollect(userArticle1);
        try {
            userArticle1.setUsername(MimeUtility.decodeText(userArticle1.getUsername()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultUtil.error(-1,"系统错误");
        }
        if (userArticle1!=null){
            return  ResultUtil.success(userArticle1);
        }
        else {
            return  ResultUtil.error(-1,"文章不存在");
        }
    }

    @ApiOperation("更新文章接口")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Result alter(@RequestBody UserArticle userArticle){
            return mArticleService.update(userArticle);
    }

    @ApiOperation("上传文章接口")
    @PostMapping
    public @ResponseBody Result addArticle(@RequestBody UserArticle userArticle, HttpSession session){
        return  mArticleService.addArticle(userArticle, session);
    }

}
