package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.DishBusinessMapper;
import com.beardream.model.DoubleDishBusiness;
import com.beardream.model.Result;
import com.beardream.service.DishBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by soft01 on 2017/5/27.
 */
@RestController
@RequestMapping("/api/mobile/dishbusiness")
@Api(value = "查找商家及菜品",description = "提供RESTful风格API的查找商家及菜品")
public class DishBusinessController {

        @Autowired
        private DishBusinessMapper dishBusinessMapper;

        @Autowired
        private DishBusinessService dishBusinessService;

        @ApiOperation("分页获取商家及菜品")
        @GetMapping("/getpage")
        public Result getPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                              DoubleDishBusiness doubleDishBusiness) {
            System.out.println(pageNum);
            System.out.println(pageSize);
            if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)) {
                return ResultUtil.error(-1, "pageNum,pageNum不能为空！");
            }
            if (dishBusinessService.getPage(doubleDishBusiness, pageNum, pageSize) != null) {
                return ResultUtil.success(dishBusinessService.getPage(doubleDishBusiness, pageNum, pageSize));
            } else {
                return ResultUtil.error(-1, "系统错误");
            }
        }

        @GetMapping
        public Result getDishBusiness(DoubleDishBusiness doubleDishBusiness) {
            if (!TextUtil.isEmpty(doubleDishBusiness.getBusinessId())) {
                return ResultUtil.error(-1, "商家ID不能为空");
            }
            if (dishBusinessService.get(doubleDishBusiness) != null) {
                return ResultUtil.success(dishBusinessService.get(doubleDishBusiness));
            } else {
                return ResultUtil.error(-1, "商家不存在");
            }
        }
    }

