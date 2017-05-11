package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.DishTypeMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.Dish;
import com.beardream.model.DishType;
import com.beardream.model.Result;
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
 * 菜品类别控制器
 */
@RestController
@RequestMapping("/dishtype")
@Api(value = "菜品分类服务",description = "提供RESTful风格API的商家的增删改查服务")
@PermissionModule(text = "菜品分类管理")
public class DishTypeController {
    @Autowired
    private DishTypeMapper dishTypeMapper;

    @ApiOperation("获取单个菜品分类信息")
    @GetMapping
    @PermissionMethod(text = "获取菜品分类信息")

    public Result get(DishType dishType, BindingResult bindingResult){
        System.out.println(dishType.getDishtypeId());
        return ResultUtil.success(dishTypeMapper.findBySelective(dishType));
    }

    @ApiOperation("添加菜品分类")
    @PostMapping
    @PermissionMethod(text = "添加菜品分类")
    public Result post(DishType dishType){
        int result;
        if (dishType==null)
            return  ResultUtil.error(-1,"没有参数");
        List<DishType> d = dishTypeMapper.findBySelective(dishType);
        if (d.size()>0)
            return ResultUtil.error(-1,"该种类的菜品已存在");
            dishType.setAddTime(new Date());
            result = dishTypeMapper.insertSelective(dishType);
        if (result>0)
            return  ResultUtil.success("成功添加菜品");
        else
            return ResultUtil.error(-1,"添加失败");
    }

    @ApiOperation("删除菜品分类")
    @DeleteMapping
    @PermissionMethod(text = "删除菜品分类")
    public Result delete(DishType dishType){
        int result;
        result = dishTypeMapper.deleteByPrimaryKey(dishType.getDishtypeId());
        if (result>0)
            return  ResultUtil.success("删除成功");
        else
            return ResultUtil.error(-1,"删除失败");
    }

    @ApiOperation("更新菜品分类")
    @PutMapping
    @PermissionMethod(text = "更新菜品分类")
    public Result update(DishType dishType){
        int result;
        System.out.println(dishType.getDishtypeId());
         dishType.setAddTime(new Date());
         result = dishTypeMapper.updateByPrimaryKeySelective(dishType);
         if (result>0)
             return ResultUtil.success("修改成功");
         else
             return ResultUtil.error(-1,"修改失败");
    }
}
