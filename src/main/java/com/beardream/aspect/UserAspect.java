package com.beardream.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by soft01 on 2017/4/17.
 */
@Aspect
@Component
public class UserAspect {

    private final static Logger logger = LoggerFactory.getLogger("IndexController.class");

    @Pointcut("execution(public * com.beardream.Controller.UserController.register(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("before log");
        //获取请求的各种信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("url={}",request.getRequestURL());

        logger.info("method={}",request.getMethod());

        logger.info("ip={}",request.getRemoteAddr());

        //类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());

        //参数
        logger.info("args={}",joinPoint.getArgs());

        //拦截确保参数正确
        Map<String,String[]> paras = request.getParameterMap();
        logger.info("param={}",paras.get("username"));
    }

    @After("log()")
    public void doAfter(){
        logger.info("after logger");
    }

//    @AfterReturning(returning = "object",pointcut = "log()")
//    public void doAfterrReturning(){
//        logger.info();
//    }
}
