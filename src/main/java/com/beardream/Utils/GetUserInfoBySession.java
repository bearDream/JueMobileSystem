package com.beardream.Utils;

import com.beardream.dao.UserMapper;
import com.beardream.model.MiniSession;
import com.beardream.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by soft01 on 2017/6/12.
 */
@Component
public class GetUserInfoBySession {

    public static User getUserInfo(UserMapper userMapper, MiniSession miniSession) {
        User sessionUser = new User();
        sessionUser.setOpenid(miniSession.getOpenid());
        List<User> user = userMapper.findSelective(sessionUser);
        if (user.size() == 0)
            return null;
        return user.get(0);
    }
}
