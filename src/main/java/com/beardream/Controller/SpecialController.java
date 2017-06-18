package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.NumberMapper;
import com.beardream.model.Number;
import com.beardream.model.Result;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by laxzh on 2017/6/16.
 * 特殊用途的控制器
 */
@RestController
@RequestMapping("/api/mobile/special")
@Api(value = "特殊服务",description = "提供RESTful风格API的特殊服务")
public class SpecialController {

    @Autowired
    private NumberMapper mNumberMapper;

    @GetMapping()
    public Result getLuckyPeople(@Param(value = "luckys") String luckys){

        // 将幸运数字拆分，并查询这些幸运数字的相关信息并放在集合中
        String[] luckyArr = luckys.split(",");


        // 根据固定商家的信息进行抽奖 （蕨菜官方店， id:64）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String now = sdf.format(new Date());
        // 根据当前时间查询到该天的所有号数，将数据返给前端
        Number number = null;
        List<Number> luckyPeople = new ArrayList();
        try {
            for (String s : luckyArr) {
                number = new Number(64, sdf.parse(now), Integer.parseInt(s));
                number.setIsExpired((byte) 0);
                List<Number> list = mNumberMapper.findBySelective(number);
                if (list.size() != 0)
                    luckyPeople.add(list.get(0));
            }
            for (Number number1 : luckyPeople) {
                number1.setUsername(MimeUtility.decodeText(number1.getUsername()));
            }
            return ResultUtil.success(luckyPeople);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultUtil.error(-1,"系统出错");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultUtil.error(-1,"系统出错");
        }

    }
}
