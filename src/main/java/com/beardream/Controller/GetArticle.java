package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.ArticleMapper;
import com.beardream.dao.BusinessMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/19.
 */
@RestController
@RequestMapping("/article")
@Api(value = "图文服务",description = "提供RESTful风格API的图文的增删改查服务")
@PermissionModule(text = "菜品管理")
public class GetArticle {
    @Autowired
    private ArticleMapper articleMapper;

    @ApiOperation("分页获取用户图文")
    @PutMapping("/getarticle")
    @PermissionMethod(text = "分页获取用户图文信息")
    public Map getPage(Article article, int pageNum, int pageSize){
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<Article> articles = articleMapper.findBySelective(new Article());
        PageInfo page = new PageInfo(articles);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        return map;
    }
}
