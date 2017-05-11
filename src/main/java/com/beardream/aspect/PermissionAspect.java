package com.beardream.aspect;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.LogMapper;
import com.beardream.dao.RoleMapper;
import com.beardream.enums.ResultEnum;
import com.beardream.exception.PermissionException;
import com.beardream.exception.UserException;
import com.beardream.model.Log;
import com.beardream.model.Role;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/8.
 */
@Aspect
@Component
public class PermissionAspect {

    /*
        权限拦截时，根据用户的角色来查询相应的权限
        1、拿到用户的roleID
        2、根据roleId查询拥有的权限，兵将所有权限根据“,”分割开来放到数组中
        3、将要访问的/模块/方法在数组中遍历判断是否拥有相应的权限，如果有权限则继续走，否则拦截
 */

    @Autowired
    private RoleMapper mRoleMapper;

    private final static Logger logger = LoggerFactory.getLogger("PermissionAspect.class");

    @Pointcut("execution(* com.beardream.Controller..*(..)) && @annotation(com.beardream.ioc.PermissionMethod)")
    public void permission(){}

    @Before("permission()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("before log");
        //获取请求的各种信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        logger.info("ok={}","权限拦截器已经进来了");

        HttpSession session = request.getSession();

        session = request.getSession();
        if (session.getAttribute(Constants.USER) == null){
            //未登录
            throw new UserException(ResultEnum.Logout);
        }

        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        System.out.println("用户的角色id："+user.getRoleId());

        Role role = mRoleMapper.selectByPrimaryKey(user.getRoleId());
        System.out.println(role.getPromission());

        //将用户的权限存到userPermission数组中
        String[] userPermission = role.getPromission().split(",");

        String accessPermission = request.getRequestURI() + "/" + TextUtil.toLowerStr(request.getMethod());
        System.out.println("访问的是这个："+accessPermission);

        boolean can_access = false;
        for (int i = 0 ; i < userPermission.length ; i++){
            if (userPermission[i].equals(accessPermission)){
                can_access = true;
                break;
            }
        }

        if (!can_access){
            //进行拦截
            throw new PermissionException(ResultEnum.NOPERMISSION);
        }



    }

}
