package com.beardream.service;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Ip;
import com.beardream.Utils.MD5;
import com.beardream.Utils.ResultUtil;
import com.beardream.dao.UserMapper;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by soft01 on 2017/4/28.
 */
@Component
@Service
public class LoginService {

    @Autowired
    public UserMapper mUserMapper;

/*
    public void login() {
        User user = getModel(User.class, "");
        switch (getPara("loginfield")) {
            case "WX":
                //根据用户用户名查找用户
                User user1 = User.dao.findFirst("select a.* from t_user a where a.openid=?",getPara("userid"));
                if (user1 == null) {
                    setAttr("success", false);
                    setAttr("message", "未绑定微信号");
                    ReturnKit.getRender(this);
                    return;
                }
                if (user1.getStatus() != 0) {
                    setAttr("success", false);
                    setAttr("message", "用户已禁用");
                    ReturnKit.getRender(this);
                    return;
                }
                if (user1.getType() != 0 && user1.getType() != 1) {
                    setAttr("success", false);
                    setAttr("message", "该用户不属于会员或医生");
                    ReturnKit.getRender(this);
                    return;
                }
                UserDetail userDetail = UserDetail.dao.findById(user1.getInt("user_id"));
                if (userDetail == null) {
                    setAttr("success", false);
                    setAttr("message", "用户不存在");
                    ReturnKit.getRender(this);
                    return;
                } else {
                    userDetail.set("last_login_date", new Date());
                    userDetail.set("last_login_ip", Ip.getRemoteLoginUserIp(getRequest()));
                    userDetail.set("logins", userDetail.getInt("logins") + 1);
                    userDetail.update();
                }
                if (CommonFunction.getPermission(user1.getInt("user_id"), getSession())) {
                    user1 = User.dao.findFirst("select d.*,a.*,l.level_name from t_user a inner join t_user_detail d on d.user_id=a.user_id left join t_level l on l.level_id=d.level_id where a.user_id=?", user1.getUserId());
                    setSessionAttr(Constants.SESSION_USER, user1);
                    setCookie("user_id", user1.getInt("user_id") + "", 7 * 3600 * 24, JFinal.me().getContextPath());
                    setCookie("password", user1.getStr("password"), 7 * 3600 * 24, JFinal.me().getContextPath());
                    user1.remove("password");
                    setAttr("message", user1);
                    setAttr("success", true);
                    ReturnKit.getRender(this);
                } else {
                    setAttr("message", "用户权限获取失败，登录失败");
                    setAttr("success", false);
                    ReturnKit.getRender(this);
                }
                break;
            case "QQ":
                //根据用户用户名查找用户
                User user2 = User.dao.findFirst("select a.* from t_user a where a.qquserid=?",getPara("userid"));
                if (user2 == null) {
                    setAttr("success", false);
                    setAttr("message", "未绑定微信号");
                    ReturnKit.getRender(this);
                    return;
                }
                if (user2.getStatus() != 0) {
                    setAttr("success", false);
                    setAttr("message", "用户已禁用");
                    ReturnKit.getRender(this);
                    return;
                }
                if (user2.getType() != 0 && user2.getType() != 1) {
                    setAttr("success", false);
                    setAttr("message", "该用户不属于会员或医生");
                    ReturnKit.getRender(this);
                    return;
                }
                UserDetail userDetail1 = UserDetail.dao.findById(user2.getInt("user_id"));
                if (userDetail1 == null) {
                    setAttr("success", false);
                    setAttr("message", "用户不存在");
                    ReturnKit.getRender(this);
                    return;
                } else {
                    userDetail1.set("last_login_date", new Date());
                    userDetail1.set("last_login_ip", Ip.getRemoteLoginUserIp(getRequest()));
                    userDetail1.set("logins", userDetail1.getInt("logins") + 1);
                    userDetail1.update();
                }
                if (CommonFunction.getPermission(user2.getInt("user_id"), getSession())) {
                    user2 = User.dao.findFirst("select d.*,a.*,l.level_name from t_user a inner join t_user_detail d on d.user_id=a.user_id left join t_level l on l.level_id=d.level_id where a.user_id=?", user2.getUserId());
                    setSessionAttr(Constants.SESSION_USER, user2);
                    setCookie("user_id", user2.getInt("user_id") + "", 7 * 3600 * 24, JFinal.me().getContextPath());
                    setCookie("password", user2.getStr("password"), 7 * 3600 * 24, JFinal.me().getContextPath());
                    user2.remove("password");
                    setAttr("message", user2);
                    setAttr("success", true);
                    ReturnKit.getRender(this);
                } else {
                    setAttr("message", "用户权限获取失败，登录失败");
                    setAttr("success", false);
                    ReturnKit.getRender(this);
                }
                break;
            default:
                //判断验证码是否正确
                if (!validateCaptcha("captcha")) {
                    setAttr("success", false);
                    setAttr("message", "验证码不正确");
                    ReturnKit.getRender(this);
                    return;
                }
                //根据用户用户名查找用户
                User user3 = User.dao.findFirst("select a.* from t_user a where a." + getPara("loginfield") + "=?", user.getStr("username"));
                if (user3 == null) {
                    setAttr("success", false);
                    setAttr("message", "用户名不存在");
                    ReturnKit.getRender(this);
                    return;
                }
                if (user3.getStatus() != 0) {
                    setAttr("success", false);
                    setAttr("message", "用户已禁用");
                    ReturnKit.getRender(this);
                    return;
                }
                if (user3.getType() != 0 && user3.getType() != 1) {
                    setAttr("success", false);
                    setAttr("message", "该用户不属于会员或医生");
                    ReturnKit.getRender(this);
                    return;
                }
                //判断用户密码是否正确
                if (Validate.isNull(user.getStr("password")) || !user3.getStr("password").equals(MD5.GetMD5Code(user.getStr("password")))) {
                    setAttr("success", false);
                    setAttr("message", "密码不正确");
                    ReturnKit.getRender(this);
                    return;
                }
                UserDetail userDetail2 = UserDetail.dao.findById(user3.getInt("user_id"));
                if (userDetail2 == null) {
                    setAttr("success", false);
                    setAttr("message", "用户不存在");
                    ReturnKit.getRender(this);
                    return;
                } else {
                    userDetail2.set("last_login_date", new Date());
                    userDetail2.set("last_login_ip", Ip.getRemoteLoginUserIp(getRequest()));
                    userDetail2.set("logins", userDetail2.getInt("logins") + 1);
                    userDetail2.update();
                }
                if (CommonFunction.getPermission(user3.getInt("user_id"), getSession())) {
                    user3 = User.dao.findFirst("select d.*,a.*,l.level_name from t_user a inner join t_user_detail d on d.user_id=a.user_id left join t_level l on l.level_id=d.level_id where a.user_id=?", user3.getUserId());
                    setSessionAttr(Constants.SESSION_USER, user3);
                    setCookie("user_id", user3.getInt("user_id") + "", 7 * 3600 * 24, JFinal.me().getContextPath());
                    setCookie("password", user3.getStr("password"), 7 * 3600 * 24, JFinal.me().getContextPath());
                    user3.remove("password");
                    setAttr("message", user3);
                    setAttr("success", true);
                    ReturnKit.getRender(this);
                } else {
                    setAttr("message", "用户权限获取失败，登录失败");
                    setAttr("success", false);
                    ReturnKit.getRender(this);
                }
                break;
        }
    }
 */


    public Result login(User user, HttpServletRequest request, HttpSession session){

        //根据用户用户名查找用户
//        User userInfo = mUserMapper.findSelective(user).get(0);
//        if (userInfo == null) {
//            return ResultUtil.error(-1, "未绑定微信号");
//        }
//        if (userInfo.getStatus() != 0) {
//            return ResultUtil.error(-1, "用户已禁用");
//        }
//
//        userInfo.setLogins(userInfo.getLogins()+1);
//        mUserMapper.updateByPrimaryKeySelective(userInfo);
//
//        if (CommonFunction.getPermission(user1.getInt("user_id"), getSession())) {
//            user1 = User.dao.findFirst("select d.*,a.*,l.level_name from t_user a inner join t_user_detail d on d.user_id=a.user_id left join t_level l on l.level_id=d.level_id where a.user_id=?", user1.getUserId());
//            setSessionAttr(Constants.SESSION_USER, user1);
//            setCookie("user_id", user1.getInt("user_id") + "", 7 * 3600 * 24, JFinal.me().getContextPath());
//            setCookie("password", user1.getStr("password"), 7 * 3600 * 24, JFinal.me().getContextPath());
//            user1.remove("password");
//            setAttr("message", user1);
//            setAttr("success", true);
//            ReturnKit.getRender(this);
//        } else {
//            setAttr("message", "用户权限获取失败，登录失败");
//            setAttr("success", false);
//            ReturnKit.getRender(this);
//        }
//
//
//
//
//
//
//


        if (user == null){
            throw new NullPointerException("user不能为空");
        }
        User user1 = mUserMapper.findByMobile(user.getTel());
        if(user1 == null){
            //用户名不存在
            return ResultUtil.error(-1,"用户不存在");
        }
        System.out.println(user1.getUserId());
        System.out.println(user1.getTel());
        if(user1.getPassword().equals(MD5.GetMD5Code(user.getPassword()))){
            //生成token
//            String token = user.getTel() + user.getPassword() + System.currentTimeMillis();
//            token = MD5.GetMD5Code(token);
            user1.setLogins(user1.getLogins()+1);
            Gson gson = new Gson();

            //登录成功
            session.setAttribute(Constants.USER, gson.toJson(user1));
            session.setMaxInactiveInterval(15*24*60*60);//单位是秒   15*24*60*60十五天的过期时间
            System.out.println("登录成功");
            return ResultUtil.success(0,"登录成功");
        }
        return ResultUtil.error(-1,"登录失败");



    }
}
