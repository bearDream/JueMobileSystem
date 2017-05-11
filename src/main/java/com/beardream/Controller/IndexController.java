package com.beardream.Controller;

import com.beardream.dao.UserMapper;
import com.beardream.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by soft01 on 2017/3/22.
 * 首页控制器
 */
@RestController
public class IndexController {

    @GetMapping(value = "/index")
    public String index(){
        return "/";
    }

}
