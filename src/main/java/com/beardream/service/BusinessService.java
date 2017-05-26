package com.beardream.service;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.Sort;
import com.beardream.dao.BusinessMapper;
import com.beardream.model.Business;
import com.beardream.model.Number;
import com.beardream.model.Result;
import com.beardream.model.UserArticle;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by soft01 on 2017/5/17.
 */
@Component
@Service
public class BusinessService {

    @Autowired
    public BusinessMapper mBussinessMapper;

    @Autowired TakeNumService mTakeNumService;

    //获取单个商家信息
    public Result find(Business business){
        List<Business> businessInfo = mBussinessMapper.findBySelective(business);
        if (businessInfo.size() == 0)
            return ResultUtil.error(-1,"商家不存在");
        else
            return ResultUtil.success(businessInfo.get(0));
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
        return map;
    }

    public List<Business> getBusinessTakeInfoSort(Map businessMap, String waitSort){

        PageInfo p = (PageInfo) businessMap.get("page");
        List<Business> businessList = p.getList();

        for (Business business : businessList){
            // 根据businessId查询该商家的排队人数
            int wait = mTakeNumService.getBusinessNum(business).size();
            business.setWait(wait);
        }

        // 将等待桌数进行排序
        businessList = Sort.sortBusinessDesc(businessList, waitSort);

        return businessList;
    }

}