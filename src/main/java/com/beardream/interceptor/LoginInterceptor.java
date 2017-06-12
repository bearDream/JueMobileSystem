package com.beardream.interceptor;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.dao.UserMapper;
import com.beardream.enums.ResultEnum;
import com.beardream.exception.UserException;
import com.beardream.model.User;
import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by soft01 on 2017/5/7.
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper mUserMapper;

    @Value("${env}")
    String env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println(">>>Login拦截器>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        HttpSession session = request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;

        // 开发阶段改为自动登录，不需要每次请求登录
        if (env.equals("dev")){
            Gson gson = new Gson();
            User user = mUserMapper.selectByPrimaryKey(2);
            user.setUsername(MimeUtility.decodeText(user.getUsername()));
            System.out.println(user.getUsername());
            session.setAttribute(Constants.USER, gson.toJson(user));
        }else {
            //生产模式下，执行下面代码判断是否登录
            if (session.getAttribute(Constants.USER) == null && request.getHeader("thirdSession") == null){
                //未登录
                try {
                    out = response.getWriter();
                    out.append(Json.toJson(ResultUtil.error(-1,"未登录")));
                    System.out.println("返回是\n");
                    System.out.println(ResultEnum.Logout.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                        return false;
                    }
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println(">>>Login拦截器>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println(">>>Login拦截器>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
}
