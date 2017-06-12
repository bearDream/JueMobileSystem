package com.beardream.wx.controller;

import com.beardream.Utils.*;
import com.beardream.dao.UserMapper;
import com.beardream.model.MiniSession;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.model.WxUser;
import com.beardream.service.CommonService;
import com.beardream.wx.handler.MiniHandler;
import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CommonService mCommonService;

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

        List is_reg = mUserMapper.findSelective(new User(miniSession.getOpenid()));

        // 根据openid查找该用户是否注册过，若没有注册返回错误码1，前端将再次发请求将数据注册进来
        if (is_reg.size() == 0)
            return ResultUtil.error(2,"请先注册",miniSession.getOpenid());

        // 4、生成三方session并存储微信返回的session，以三方session为key，session_key + openid为value写入session存储

        String thirdSession = MD5.GetMD5Code(miniSession.getSession_key());

        Map<String, Object> info = new HashMap<>();
        info.put("session_key", miniSession.getSession_key());
        info.put("openid", miniSession.getOpenid());
        session.setAttribute(thirdSession, info);
        session.setMaxInactiveInterval(miniSession.getExpires_in());
        System.out.println(session.getAttribute(thirdSession));
        // 5、将三方session返回给用户
        return ResultUtil.success(thirdSession);

        // 6、用户将三方session写入storage

        // 7、 后续根据三方session来查看用户是否登陆
    }

    @GetMapping("/isLogin")
    public Result isLogin(@RequestParam(value="thirdSession", required=false) String thirdSession, HttpSession session){

        if (session.getAttribute(thirdSession) == null)
            return ResultUtil.error(-1,"未登录");

        Result res = mCommonService.getUserInfoByThirdSession((Map) session.getAttribute(thirdSession));
        if(res.getCode() == -1){
            return res;
        }

        User user = (User) res.getData();
        // 2、 后续根据三方session来查看用户是否登陆
        return res;
    }

    // 将用户信息注册写入数据库
    @PostMapping("/register")
    public Result register(WxUser wxUser){

        User user =  new User();
        try {
            user.setUsername(MimeUtility.encodeText(wxUser.getNickName()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultUtil.error(-1,"注册失败");
        }
        user.setOpenid(wxUser.getOpenid());
        user.setSex(wxUser.getGender());
        user.setLogins(1);
        user.setBodyStatus((byte) 0);
        user.setAddress(wxUser.getCountry() + wxUser.getProvince() + wxUser.getCity());
        int res = mUserMapper.insert(user);
        if (res != 0)
            return ResultUtil.success("注册成功");

        return ResultUtil.error(-1,"注册失败");
    }
}
