package com.beardream.service;

import com.beardream.Utils.MD5;
import com.beardream.dao.UserMapper;
import com.beardream.enums.ResultEnum;
import com.beardream.exception.UserException;
import com.beardream.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by soft01 on 2017/4/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setUsername("测试");
        user.setPassword("1234567");

        System.out.println(user.toString());
        user.setPassword(MD5.GetMD5Code(user.getPassword()));
        int i = userMapper.insertSelective(user);
        System.out.println(i);
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void userList() throws Exception {
        List<User> list = userService.userList();
        Assert.assertEquals("张弛",list.get(0).getUsername());
    }

    @Test
    public void check(){
        User user = new User();
        user.setTel("15587186809");
        List<User> u = userMapper.findSelective(user);
        System.out.println(u.get(0).getTel());
        Assert.assertEquals("15587186809",u.get(0).getTel());
    }

}