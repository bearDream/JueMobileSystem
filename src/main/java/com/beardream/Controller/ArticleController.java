package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.ArticleMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.*;
import com.beardream.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by soft01 on 2017/5/19.
 */
@RestController
@RequestMapping("/api/mobile/article")
@Api(value = "图文服务",description = "提供RESTful风格API的图文的增删改查服务")
@PermissionModule(text = "菜品管理")
public class ArticleController {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleService articleService;

    @ApiOperation("分页获取用户图文")
    @PutMapping("/getpage")
    @PermissionMethod(text = "分页获取用户图文信息")
    public Result getPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false)  int pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false)  int pageSize,
                          UserArticle userArticle){
        System.out.println(pageNum);
        System.out.println(pageSize);
        if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)){
            return ResultUtil.error(-1,"pageNum,pageNum不能为空！");
        }
        if (articleService.getPage(userArticle,pageNum,pageSize)!=null){
            return ResultUtil.success(articleService.getPage(userArticle,pageNum,pageSize));
        }
        else {
            return ResultUtil.error(-1,"系统错误");
        }
    }

    @GetMapping
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
