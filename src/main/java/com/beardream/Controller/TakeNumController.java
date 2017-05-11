package com.beardream.Controller;

import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by laxzh on 2017/5/6.
 * 取号控制器
 */
@RestController
@RequestMapping("/tag")
@Api(value = "取号服务",description = "提供RESTful风格API的取号的操作")
public class TakeNumController {


}
