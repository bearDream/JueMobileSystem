package com.beardream.service;

import com.beardream.Utils.MD5;
import com.beardream.dao.UserMapper;
import com.beardream.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by soft01 on 2017/4/18.
 */
@Component
@Service
public class UserService{

    @Autowired
    public UserMapper userMapper;

    public List<User> userList()throws Exception{
        return userMapper.findAll();
    }

    public int save(User user)throws Exception {
        user.setPassword(MD5.GetMD5Code(user.getPassword()));
        System.out.println("主键id -->"+user.getUserId());
        return userMapper.insertSelective(user);//注册成功返回1，否则会抛出异常没有返回值
    }

    //检测手机号或用户名是否有重复
    public boolean check(User user){
        System.out.println(user.getTel());
        List<User> userList = userMapper.findSelective(user);
        System.out.println(userList.size());
        if (userList.size() != 0)
            return false;
        else
            return true;
    }

    public Object get(User user)throws Exception {
        return userMapper.findSelective(user);
    }
}
