package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.DishMapper;
import com.beardream.ioc.*;
import com.beardream.model.*;
import com.beardream.service.DishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 * 菜品控制器
 */
@RestController
@RequestMapping("/dish")
@Api(value = "菜品服务",description = "提供RESTful风格API的菜品的增删改查服务")
@PermissionModule(text = "菜品管理")
public class DishController {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishService mDishService;

    @ApiOperation("获取单个菜品信息")
    @GetMapping
    @PermissionMethod(text = "获取菜品信息")
    public Result get(Dish dish, BindingResult bindingResult){
        System.out.println(dish.getDishId());
        return ResultUtil.success(mDishService.find(dish));
    }

    @ApiOperation("添加菜品")
    @PostMapping
    @PermissionMethod(text = "添加菜品信息")
    public Result post(Dish dish){
        System.out.println(dish.getDishId());
        return ResultUtil.success(mDishService.post(dish));
    }

    @ApiOperation("删除菜品")
    @DeleteMapping
    @PermissionMethod(text = "删除菜品")
    public Result delete(Dish dish){
        System.out.println(dish.getDishId());
        return ResultUtil.success(mDishService.delete(dish));
    }

    @ApiOperation("更新菜品")
    @PutMapping
    @PermissionMethod(text = "更新菜品信息")
    public Result put(Dish dish){
        System.out.println(dish.getDishId());
        return ResultUtil.success(mDishService.put(dish));
    }

    @ApiOperation("分页获取角色")
    @GetMapping("/getpage")
    @com.beardream.ioc.Log
    public Result getPage(Role role, @RequestParam(value = "pageNum", required = false)  int pageNum, @RequestParam(value = "pageSize", required = false)  int pageSize, BindingResult bindingResult){
//        System.out.println(role.getRoleId());
        System.out.println(pageNum);
        System.out.println(pageSize);
        if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)){
            return ResultUtil.error(-1,"pageNum,pageNum不能为空！");
        }

        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<Dish> dishs =dishMapper.findBySelective(new Dish());
        PageInfo page = new PageInfo(dishs);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("list",dishs);
        return ResultUtil.success(map);
    }
}
