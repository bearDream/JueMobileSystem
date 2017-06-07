package com.beardream.service;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.Sort;
import com.beardream.dao.BusinessMapper;
import com.beardream.model.*;
import com.beardream.model.Number;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by beardream on 2017/5/17.
 */
@Component
@Service
public class RandomService {

    @Autowired
    public BusinessMapper mBussinessMapper;

    /*
        v1.0.0版获取随机菜品
        由于该版获取的失败率太高因此该版作废，建议使用getRandomDishesNew版本
     */
    @Deprecated
    public Result getRandomDishes(HttpSession session){
        // 1、获取用户的，body_status来推荐相应的菜品
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        Integer bodyStatus = Integer.valueOf(user.getBodyStatus());

        // 2、查询出所有商家，随机选取一个商家，得到相应的商家id
        Business business = new Business();
        business.setIsShow((byte) 1);
        List<Business> businessList = mBussinessMapper.findBySelective(business);
        int max = businessList.size() - 1;
        int randomBusiness = businessList.get(new Random().nextInt(max)).getBusinessId();

        //3、根据商家id以及body_status来查找三个菜品
        List<DishBusiness> dishList = mBussinessMapper.findBusinessDishBySelective(bodyStatus, randomBusiness);

        if (dishList.size() <= 0){
            return ResultUtil.error(-1,"生成失败，请再试一次哦~");
        }
        return ResultUtil.success(dishList);
    }

    public Result getRandomDishesNew(HttpSession session){
        // 1、获取用户的，body_status来推荐相应的菜品
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        Integer bodyStatus = Integer.valueOf(user.getBodyStatus());

        // 2、直接连接dish_business和dish表查询得出 哪个商家可能有这三个符合用户要求的菜品
        Business business = new Business();
        business.setIsShow((byte) 1);
        List<DishBusiness> dishList = mBussinessMapper.findBusinessDishBySelective(bodyStatus, null);
        // 根据这个list遍历找到businessNum大于等于三的商家id，然后再进行随机选择
        List<DishBusiness> canRandomBusinessList = new ArrayList<>();
        for (DishBusiness dishBusiness : dishList) {
            if (dishBusiness.getBusinessNum() >= 3){
                canRandomBusinessList.add(dishBusiness);
            }
        }

        // 没有符合条件的商家则无法生成
        if (canRandomBusinessList.size() == 0)
            return ResultUtil.error(-1,"生成失败，请再试一次哦~");

        // 随机获取一个商家id来进行查找，此处的商家一定是可以生成三个菜的
        int max = canRandomBusinessList.size() - 1;
        int businessId = canRandomBusinessList.get(new Random().nextInt(max)).getBusinessId();
        List<DishBusiness> dishes = mBussinessMapper.findBusinessDishInfoBySelective(bodyStatus, businessId);

        if (dishList.size() <= 0){
            return ResultUtil.error(-1,"生成失败，请再试一次哦~");
        }
        return ResultUtil.success(dishes);
    }

}