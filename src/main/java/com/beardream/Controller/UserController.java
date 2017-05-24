package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.UserMapper;
import com.beardream.exception.UserException;
import com.beardream.ioc.Log;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by soft01 on 2017/4/17.
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "用户服务",description = "提供RESTful风格API的用户的增改查服务")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @ApiOperation("查找用户")
    public Result get(User user) throws Exception {
        return ResultUtil.success(userService.get(user));
    }

    @PostMapping()
    @ApiOperation("添加/注册用户")
    public Object register(@Valid User user, BindingResult bindingResult) throws Exception {
        System.out.println(user.getTel());
        if (userService.check(user)){
            if(userService.save(user) == 1){
                //注册成功
                return ResultUtil.success("注册成功");
            }
        }else {
            return ResultUtil.success(-1,"手机号码已被注册");
        }
        return ResultUtil.success(-1,"手机号码已被注册");
    }

    @PutMapping()
    @ApiOperation("修改用户")
    public Object alter(User user, BindingResult bindingResult){

        return ResultUtil.error(-1,"修改信息失败");
    }

}

