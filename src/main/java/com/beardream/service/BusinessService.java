package com.beardream.service;

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

import javax.persistence.Convert;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by soft01 on 2017/5/17.
 */
@Component
@Service
public class BusinessService {

    @Autowired
    public BusinessMapper mBussinessMapper;

    @Autowired
    TakeNumService mTakeNumService;

    //获取单个商家信息
    public Result find(Business business){
        List<Business> businessInfo = mBussinessMapper.findBySelective(business);
        if (businessInfo.size() == 0)
            return ResultUtil.error(-1,"商家不存在");
        else
            return ResultUtil.success(businessInfo.get(0));
    }

    // 获取某个商家的队列信息
    public List<Number> getBusinessQue(Business business){
        return mTakeNumService.getBusinessNum(business);
    }

    // 将排队信息根据人数分为三个队列（小桌，中桌，大桌）
    // 1-4个人是小桌   5-6个人是中桌   7人以上为大桌
    public Map getQueTableMap(List<Number> numberList){
        Map<String, Object> resultMap = new HashedMap();

        List<Number> smallQue = new ArrayList<>();
        List<Number> mediumQue = new ArrayList<>();
        List<Number> bigQue = new ArrayList<>();
        for (Number number : numberList) {
            if (number.getPeopleNum() >= 1 && number.getPeopleNum() <= 4)
                smallQue.add(number);
            if (number.getPeopleNum() >= 5 && number.getPeopleNum() <= 6)
                mediumQue.add(number);
            if (number.getPeopleNum() >= 7)
                bigQue.add(number);
        }

        // 再排序一次
        smallQue = Sort.sortNumberDesc(smallQue, "asc");
        mediumQue = Sort.sortNumberDesc(mediumQue, "asc");
        bigQue = Sort.sortNumberDesc(bigQue, "asc");
        // 将三个集合放到map中返回给前端
        resultMap.put("smallQue", smallQue);
        resultMap.put("mediumQue", mediumQue);
        resultMap.put("bigQue", bigQue);
        resultMap.put("allNums", smallQue.size() + mediumQue.size() + bigQue.size());
        return resultMap;
    }

    public BusinessDishTag get(BusinessDishTag businessDishTag){
        return mBussinessMapper.findBusinessDishTagBySelective(businessDishTag).get(0);
    }
    //post请求
    public Result add(Business business){
        int result;
        if (business==null)
            return ResultUtil.error(-1,"没有参数");
        List<Business> BussinessList = mBussinessMapper.findBySelective(business);
        if (BussinessList.size()>0)
            return ResultUtil.error(-1,"该商家已存在");
        business.setAddTime(new Date());
        result = mBussinessMapper.insertSelective(business);
        if (result>0){
            return ResultUtil.success("添加成功");
        }else{
            return ResultUtil.error(-1,"添加失败");
        }
    }


    public Map getPage(Business business, int pageNum, int pageSize) {
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<Business> businesses =mBussinessMapper.findBySelective(business);
        PageInfo page = new PageInfo(businesses);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("list",businesses);
        return map;
    }

    // 按照取号桌数排序
    public Map getBusinessTakeInfoSort(Map businessMap, String waitSort){

        PageInfo p = (PageInfo) businessMap.get("page");
        List<Business> businessList = p.getList();

        for (Business business : businessList){
            // 根据businessId查询该商家的排队人数
            int wait = mTakeNumService.getBusinessNum(business).size();
            business.setWait(wait);
        }

        // 将等待桌数进行排序
        businessList = Sort.sortBusinessDesc(businessList, waitSort);

        p.setList(businessList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", p);

        return map;
    }

    // 按照商家等级排序
    public Map getBusinessLevelInfoSort(Map businessMap, String waitSort){

        PageInfo p = (PageInfo) businessMap.get("page");
        List<Business> businessList = p.getList();

        for (Business business : businessList){
            // 根据businessId查询该商家的排队人数
            int wait = mTakeNumService.getBusinessNum(business).size();
            business.setWait(wait);
        }

        businessList = Sort.sortBusinessLevel(businessList, "desc");

        p.setList(businessList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", p);

        return map;
    }

    // 按照附近距离排序
    public Map getBusinessLocationSort(Map businessMap, double latitude, double longtitude, String waitSort){

        PageInfo p = (PageInfo) businessMap.get("page");
        List<Business> businessList = p.getList();

        // 使用NumberFormat去小数后两位
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);

        for (Business business : businessList){
            // 根据businessId,用户的经纬度 查询该商家到用户的距离
            Business businessDistance = mBussinessMapper.findDistanceBySelective(latitude, longtitude, business.getBusinessId());
//            Business businessDistance = mBussinessMapper.findDistanceBySelective();
            business.setDistance(Double.parseDouble(nf.format(businessDistance.getDistance() / 1000)));
        }

        businessList = Sort.sortBusinessDistance(businessList, "asc");

        p.setList(businessList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", p);

        return map;
    }

    public Map getBusinessDishList(Map businessMap, int bodyStatus){
        PageInfo p = (PageInfo) businessMap.get("page");
        List<Business> businessList = p.getList();

        // 遍历商家 查询商家拥有的菜品信息
        for (Business business : businessList) {
            List<DishBusiness> dishBusinessList = mBussinessMapper.findBusinessDishBySelective(bodyStatus, business.getBusinessId());
            // 最多设置两个菜品，但以防查出来的该商家只有1个或零个菜品而造成数组越界问题
            if (dishBusinessList.size() == 0) continue;
            if (dishBusinessList.size() ==1) {
                business.setOneDishId(dishBusinessList.get(0).getDishId());
                business.setOneDishName(dishBusinessList.get(0).getDishName());
                business.setOneDishRecImage(dishBusinessList.get(0).getDishRecImage());
            }
            if (dishBusinessList.size() >= 2) {
                business.setOneDishId(dishBusinessList.get(0).getDishId());
                business.setOneDishName(dishBusinessList.get(0).getDishName());
                business.setOneDishRecImage(dishBusinessList.get(0).getDishRecImage());
                business.setTwoDishId(dishBusinessList.get(1).getDishId());
                business.setTwoDishName(dishBusinessList.get(1).getDishName());
                business.setTwoDishRecImage(dishBusinessList.get(1).getDishRecImage());
            }
        }

        p.setList(businessList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", p);
        return map;
    }

}