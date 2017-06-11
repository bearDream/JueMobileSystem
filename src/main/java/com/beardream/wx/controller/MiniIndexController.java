package com.beardream.wx.controller;

import com.beardream.Utils.*;
import com.beardream.dao.UserMapper;
import com.beardream.model.MiniSession;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.wx.handler.MiniHandler;
import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laxzhang on 2017/5/12.
 * 微信app进入入口，判断是否注册过，没有则执行自动注册
 */
@RestController
@RequestMapping("/api/mobile/mini")
public class MiniIndexController {

    @Value("${domain}")
    String domain;
    @Value("${appid}")
    String appid;
    @Value("${appSecret}")
    String appSecret;
    @Value("${miniAppId}")
    String miniAppId;
    @Value("${miniSecret}")
    String miniSecret;

    @Autowired
    private UserMapper mUserMapper;

    private final static Logger logger = LoggerFactory.getLogger("MiniIndexController.class");

    @GetMapping
    public Result indexMini(@RequestParam(value="code", required=false) String code, HttpSession session){

        // 1、拿到用户的code

        // 2、将appid + appsecret + code 发送给微信https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String result = new MiniHandler().getMiniSession(miniAppId, miniSecret, code);

        // 3、接收微信返回的session_key + openid
        System.out.println("获取到用户的信息：");
        System.out.println(result);
        MiniSession miniSession = new Gson().fromJson(result, MiniSession.class);
        System.out.println(miniSession.getSession_key());
        System.out.println(miniSession.getOpenid());
        // 4、生成三方session并存储微信返回的session，以三方session为key，session_key + openid为value写入session存储

        String thirdSession = MD5.GetMD5Code(miniSession.getSession_key());

        List info = new ArrayList();
        info.add(miniSession.getSession_key());
        info.add(miniSession.getOpenid());
        session.setAttribute(thirdSession, info);
        session.setMaxInactiveInterval(miniSession.getExpires_in());
        // 5、将三方session返回给用户
        return ResultUtil.success(thirdSession);

        // 6、用户将三方session写入storage

        // 7、 后续根据三方session来查看用户是否登陆
    }

    @GetMapping("/isLogin")
    public Result isLogin(@RequestParam(value="thirdSession", required=false) String thirdSession, HttpSession session){

        // 1、用户将三方session写入storage
        if (session.getAttribute(thirdSession) == null)
            return ResultUtil.error(-1,"未登录");

        MiniSession miniSession = new Gson().fromJson((String) session.getAttribute(thirdSession), MiniSession.class);
        User user = new User();
        user.setOpenid(miniSession.getOpenid());
        User userInfo = mUserMapper.findSelective(user).get(0);
        // 2、 后续根据三方session来查看用户是否登陆
        return ResultUtil.success(userInfo);
    }
}
