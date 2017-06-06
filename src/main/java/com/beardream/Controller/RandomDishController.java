package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.BusinessMapper;
import com.beardream.model.*;
import com.beardream.model.Number;
import com.beardream.service.BusinessService;
import com.beardream.service.RandomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 * 商家控制器
 */
@RestController
@RequestMapping("/api/mobile/random")
@Api(value = "今天吃啥服务",description = "提供RESTful风格API的今天吃啥的增删改查服务")
public class RandomDishController {

    @Autowired
    private RandomService mRandomService;

    @ApiOperation("获取今天吃啥的三个菜")
    @GetMapping
    public Result get(HttpSession session) {
        // 根据用户的特征body_status来获取不同的三个菜品
        return mRandomService.getRandomDishesNew(session);
    }
}
