package com.beardream.aspect;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.dao.LogMapper;
import com.beardream.enums.ResultEnum;
import com.beardream.exception.UserException;
import com.beardream.model.Log;
import com.beardream.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 */
@Aspect
@Component
public class LogAspect {

    /*
        日志拦截时，先判断是否登录，没有登录则打回去要求登录
        如果登录则将日志写入数据库
     */
    @Autowired
    private LogMapper logMapper;

    private final static Logger logger = LoggerFactory.getLogger("LogAspect.class");

    @Pointcut("execution(* com.beardream.Controller..*(..)) && @annotation(com.beardream.ioc.Log)")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("before log");
        //获取请求的各种信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("ok={}","日志拦截器已经进来了");
    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint){
        logger.info("日志拦截器 after, 将日志数据写入数据库");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();

        session = request.getSession();
        if (session.getAttribute(Constants.USER) == null){
            //未登录
            throw new UserException(ResultEnum.Logout);
        }

        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        Log log = new Log();

        log.setLogAddtime(new Date());
        log.setActionkey(request.getMethod());
        String username = "";
        //如果username没有则去电话号码做标识，否则用用户名做标识
        username = (user.getUsername()==null) ? user.getTel() : user.getUsername();
        log.setLogContent(username+"于"+new Date()+"操作了"+ request.getRequestURI() +"方法");
        log.setControllerkey(request.getRequestURI());
        log.setUserId(user.getUserId());

        Map<String, String[]> map = request.getParameterMap();
        StringBuilder params = new StringBuilder();
        //不管是post还是get都可以获取参数
        for (String name : map.keySet()){
            String[] values = map.get(name);
            params.append(name+"="+Arrays.toString(values).substring(1, Arrays.toString(values).length()-1)+",");
        }
        if (params.length() > 0)
            params = new StringBuilder(params.substring(0,params.length()-1));
        log.setParams(params.toString());
        log.setLogIp(request.getRemoteAddr());
        logMapper.insertSelective(log);
    }

//    @AfterReturning(returning = "object",pointcut = "log()")
//    public void doAfterrReturning(){
//        logger.info();
//    }
}
