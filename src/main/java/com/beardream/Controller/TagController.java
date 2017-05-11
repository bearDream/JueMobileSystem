package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.TagMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.Business;
import com.beardream.model.Result;
import com.beardream.model.Tag;
import com.beardream.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by laxzh on 2017/5/6.
 * 标签控制器
 */
@RestController
@RequestMapping("/tag")
@Api(value = "标签服务",description = "提供RESTful风格API的标签的增删改查服务")
@PermissionModule(text = "标签管理")
public class TagController {
    @Autowired
    private TagMapper tagMapper;

    @ApiOperation("获取单个标签")
    @GetMapping
    @PermissionMethod(text = "获取标签信息")
    public Result get(Tag tag, BindingResult bindingResult){
        System.out.println(tag.getTagId());
        return ResultUtil.success(tagMapper.findBySelective(tag));
    }

    @ApiOperation("添加标签")
    @PostMapping
    @PermissionMethod(text = "添加标签")
    public Result add(Tag tag){
        int result;
        if (tag==null)
            return  ResultUtil.error(-1,"没有参数");
        List<Tag> t = tagMapper.findBySelective(tag);
        if (t.size()>0)
            return ResultUtil.error(-1,"标签已存在");
        tag.setAddTime(new Date());
        result = tagMapper.insertSelective(tag);
        if (result>0)
            return ResultUtil.success("添加成功");
        else
            return  ResultUtil.error(-1,"添加失败");

    }

    @ApiOperation("删除标签")
    @DeleteMapping
    @PermissionMethod(text = "删除标签")
    public Result delete(Tag tag){
        int result;
        result = tagMapper.deleteByPrimaryKey(tag.getTagId());
        if (result> 0 )
            return ResultUtil.success("删除成功");
        else
            return ResultUtil.error(-1,"删除失败");
    }

    @ApiOperation("更新标签")
    @PutMapping
    @PermissionMethod(text = "更新标签")
    public Result update(Tag tag){
        int result;
        System.out.println(tag.getTagId());
        tag.setAddTime(new Date());
        result = tagMapper.updateByPrimaryKeySelective(tag);
        if (result>0)
            return  ResultUtil.success("更新成功");
        else
            return  ResultUtil.error(-1,"更新失败");
    }
}
