package com.beardream.service;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.ArticleMapper;
import com.beardream.dao.OrderMapper;
import com.beardream.model.Order;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.model.UserArticle;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by soft01 on 2017/5/24.
 */
@Component
@Service
public class OrderService {

    @Autowired
    private OrderMapper mOrderMapper;

    public Result order(Order order){
        order.setOrderStatus((byte) 0);
        int res = mOrderMapper.insertSelective(order);
        if (res > 0)
            return ResultUtil.success("订单已入库");
        return ResultUtil.error(-1,"订单入库失败");
    }

    public Result update(Order order){
        int result = mOrderMapper.updateByPrimaryKeySelective(order);
        if (result > 0)
            return ResultUtil.success("更新成功");
        return ResultUtil.error(-1,"更新失败");
    }
}
