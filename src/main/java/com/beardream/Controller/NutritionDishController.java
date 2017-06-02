package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.DishMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.model.Dish;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.service.DishService;
import com.beardream.service.NutritionDishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 * 根据不同人群推送不同营养的菜品  控制器
 */
@RestController
@RequestMapping("/api/mobile/nutritionDish")
@Api(value = "营养价值菜品服务",description = "提供RESTful风格API的营养价值菜品服务的查询服务")
public class NutritionDishController {


    @Autowired
    private NutritionDishService mNutritionDishService;

    @ApiOperation("获取今天最适合吃的菜品分页信息")
    @GetMapping
    public Result get(@RequestParam(value = "pageNum", defaultValue = "1")  int pageNum, @RequestParam(value = "pageSize", defaultValue = "10")  int pageSize,
                      HttpSession session){
        if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)){
            return ResultUtil.error(-1,"pageNum,pageNum不能为空！");
        }

        // 先根据session获取出用户的body_status状态
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        Dish dish = new Dish();
        dish.setDishNutritionStatus(Integer.valueOf(user.getBodyStatus()));

        Map dishMap = mNutritionDishService.getdishs(pageNum, pageSize, dish);

        return ResultUtil.success(dishMap);
    }

    @ApiOperation("获取营养价值菜品排行")
    @GetMapping("/get")
    public Result getNutritionDish(Dish dish){

        List<Dish> dishList = mNutritionDishService.getdishlist(dish);
        if (dishList.size() == 0)
            return ResultUtil.error(-1,"数据拉取失败");
        return ResultUtil.success(dishList.get(0));
    }

    @ApiOperation("获取营养价值菜品排行")
    @GetMapping("/rank")
    public Result getNutritionRank(Dish dish){

        List<Dish> dishList = mNutritionDishService.getdishlist(dish);
        if (dishList.size() == 0)
            return ResultUtil.error(-1,"数据拉取失败");
        return ResultUtil.success(dishList);
    }
}
