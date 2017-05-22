package com.beardream.wx.controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.dao.UserMapper;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.google.gson.Gson;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by soft01 on 2017/5/12.
 */
@RestController
@RequestMapping("/api/")
public class WxLoginController {

    private final static Logger logger = LoggerFactory.getLogger("WxLoginController.class");

    private UserMapper mUserMapper;

    @Value("${domain}")
    String domain;
    @Value("${appid}")
    String appid;
    @Value("${appSecret}")
    String appSecret;

    @Autowired
    private WxMpService mWxMpService;

    @GetMapping("/userInfo")
    public Result is_Login(HttpSession session) {
        if (session.getAttribute(Constants.USER) == null){
            return ResultUtil.error(-1,"未登录，请重新进行微信授权登录");
        }else {
            Gson gson = new Gson();
            User user = gson.fromJson((String) session.getAttribute(Constants.USER), User.class);
            return ResultUtil.success(gson.toJson(user));
        }
    }
}
