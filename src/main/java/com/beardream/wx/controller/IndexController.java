package com.beardream.wx.controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.UserMapper;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by laxzhang on 2017/5/12.
 * 微信app进入入口，判断是否注册过，没有则执行自动注册
 */
@Controller
@RequestMapping("/api/mobile")
public class IndexController {

    @Value(("${loginDomain}"))
    String loginDomain;
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
    private WxMpService mWxMpService;

    private final static Logger logger = LoggerFactory.getLogger("IndexController.class");

    @GetMapping("/redirect")
    public ModelAndView indexCode(){
        RedirectView redirectView = new RedirectView();
        StringBuffer url = new StringBuffer();
        url.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
        url.append(appid);
        url.append("&redirect_uri=");
        url.append(loginDomain);
        url.append("&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        logger.info("重定向 url={}", url);
        redirectView.setUrl(url.toString());
        return new ModelAndView(redirectView);
    }

    @GetMapping
    public ModelAndView index(@RequestParam(value="url", required=false) String url,
                              @RequestParam(value="code", required=true) String code,
                              HttpSession session,
                              HttpServletRequest request,
                              HttpResponse response) throws UnsupportedEncodingException {

        logger.info("url={}",request.getRequestURL());
        logger.info("uri={}",request.getRequestURI());
        logger.info("code={}",code);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(domain);
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = mWxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = mWxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            logger.info("username={}",wxMpUser.getNickname());
            logger.info("openid={}",wxMpUser.getOpenId());
            logger.info("city={}",wxMpUser.getCity());

            //验证ACCESS_TOKEN是否有效
            if (mWxMpService.oauth2validateAccessToken(wxMpOAuth2AccessToken)){
                User base = null;
                // 判断用户事都登录过，登录过则获取session中的数据
                if (session.getAttribute(Constants.USER) != null){
                    base = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
                }
                /*
                    判断是否登录
                    1、已登录...
                    2、未登录
                        2.1 用户session过期
                        2.2 用户没有注册
                 */
                if (base == null) {
                    //如果没有登录，则重定向进入登录控制器
                    if(login(wxMpUser, session))
                        return new ModelAndView(redirectView);//登录后重定向到应用页面
                    else
                        return new ModelAndView(new RedirectView(URLEncoder.encode("error"+request.getQueryString(), "UTF-8")));
                } else {
                    //已登录则进入app主页
                    if (TextUtil.isBlank(url)) {
                        return new ModelAndView(redirectView);
                    } else {
                        return new ModelAndView(redirectView);
                    }
                }
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
            return new ModelAndView(redirectView);
        }
        //出错的情况
        return new ModelAndView(redirectView);
    }

    public boolean login(WxMpUser wxMpUser,  HttpSession session) throws UnsupportedEncodingException {
        logger.info("this is loginController");
        String redirect_uri = URLEncoder.encode(domain + "/", "UTF-8");

        /*根据openid查找用户是否注册过
            2.1 用户session过期
            2.2 用户没有注册
         */
        Gson gson = new Gson();
        try {
            User user = new User();
//            WxMpUser wxMpUser = mWxMpService.getUserService().userInfo(openid);
            logger.info("wxMpUser nickName={}", wxMpUser.getNickname());
            user.setOpenid(wxMpUser.getOpenId());
            logger.info("openid={}", user.getOpenid());
            List<User> users = mUserMapper.findSelective(user);
            if (users.size() > 0){
                //用户session过期，执行登录后返回
                user = users.get(0);
                logger.debug("user.getOpenid()={}", user.getOpenid());
                logger.debug("user.getHeadImgUrl()={}", user.getHeadImgUrl());
                logger.debug("session user:={}", user.getUsername());
                session.setAttribute(Constants.USER, gson.toJson(user));
                session.setMaxInactiveInterval(-1);// 永远不会过期
            }else {
                //用户没有注册
                if(wxMpUser.getNickname() == null) {
                    logger.debug("获取不到用户的nickName");
                    user.setUsername(MimeUtility.encodeText("蕨菜新用户"));//防止获取不到微信名，再使用加密会出现空指针异常
                }
                user.setUsername(MimeUtility.encodeText(wxMpUser.getNickname()));
                user.setSex(wxMpUser.getSex());
                user.setAddress(wxMpUser.getCountry() + " " + wxMpUser.getCity());
                user.setHeadImgUrl(wxMpUser.getHeadImgUrl());
                user.setRemark("微信自动注册");
                int result = mUserMapper.insertSelective(user);
                if (result > 0){
                    logger.info("session的username：{}", user.getUsername());
                    session.setAttribute(Constants.USER, gson.toJson(user));
                    session.setMaxInactiveInterval(60*60*1);// 一分钟的有效时间，刚注册的用户的session不能太长
                }else {
                    //注册失败，返回到错误页面
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("error={}",e.getMessage());
            return false;
        }
        //正常情况下完成所有逻辑后返回首页
        return true;
    }
}
