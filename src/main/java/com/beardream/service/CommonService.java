package com.beardream.service;

import com.beardream.Utils.GetUserInfoBySession;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.UserMapper;
import com.beardream.model.MiniSession;
import com.beardream.model.Result;
import com.beardream.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/6/12.
 */
@Service
public class CommonService {

    @Autowired
    private UserMapper mUserMapper;

    public Result getUserInfoByThirdSession(Map thirdSession) {

        if (thirdSession == null)
            return ResultUtil.error(-1,"未登录");

        // 将session集合转换为json对象
        String sessionStr = Json.toJson(thirdSession);

        MiniSession miniSession = Json.fromJson(sessionStr, MiniSession.class);
        User user = GetUserInfoBySession.getUserInfo(mUserMapper, miniSession);
        if (user == null)
            return ResultUtil.error(-1,"用户不存在");
        return ResultUtil.success(user);
    }
}
