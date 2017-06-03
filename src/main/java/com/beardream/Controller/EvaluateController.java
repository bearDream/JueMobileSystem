package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.dao.UserCollectionMapper;
import com.beardream.model.Evaluate;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.model.UserCollection;
import com.beardream.service.CollectionService;
import com.beardream.service.EvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by laxzh on 2017/5/6.
 * 收藏控制器
 */
@RestController
@RequestMapping("/api/mobile/evaluate")
@Api(value = "评价服务",description = "提供RESTful风格API的评价的增删改查服务")
public class EvaluateController {

    @Autowired
    private EvaluateService mEvaluateService;

    @ApiOperation("发表评价")
    @PostMapping
    public Result post(UserCollection userCollection) {
        //TODO
        return ResultUtil.success("");
    }


    @ApiOperation("获取评价list")
    @GetMapping
    public Result getPage(Evaluate evaluate) {
        // 根据object_id 和 evaluate_type 来决定获取的评价
        List<Evaluate> evaluateList = mEvaluateService.getEvaluateList(evaluate);
        if (evaluateList.size() != 0)
            return ResultUtil.success(evaluateList);
        return ResultUtil.error(-1,"还没有人来评价哦");
    }

}
