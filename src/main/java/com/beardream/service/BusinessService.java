package com.beardream.service;

import com.beardream.dao.BusinessMapper;
import com.beardream.model.Business;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/17.
 */
@Component
@Service
public class BusinessService {

    @Autowired
    public BusinessMapper mBussinessMapper;

    //获取单个商家信息
    public Business find(Business business){
        System.out.println(mBussinessMapper.selectByPrimaryKey(1));
        Business BusinessInfo = mBussinessMapper.findBySelective(business).get(0);
        return BusinessInfo;
    }

    //post请求
    public String add(Business business){
        int result;
        if (business==null)
            return "没有参数";
        List<Business> BussinessList = mBussinessMapper.findBySelective(business);
        if (BussinessList.size()>0)
            return "该商家已存在";
        business.setAddTime(new Date());
        result = mBussinessMapper.insertSelective(business);
        if (result>0){
            return "添加成功";
        }else{
            return "添加失败";
        }
    }

    //删除
    public String delete(Business business){
        int result;
        result = mBussinessMapper.deleteByPrimaryKey(business.getBusinessId());
        if (result > 0) {
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    public String put(Business business){
        int result;
        System.out.println(business.getBusinessId());
        result = mBussinessMapper.updateByPrimaryKeySelective(business);
        if (result > 0) {
            return "更新成功";
        }else {
            return "更新失败";
        }
    }

    public Map getPage(Business business, int pageNum, int pageSize) {
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<Business> businesses =mBussinessMapper.findBySelective(new Business());
        PageInfo page = new PageInfo(businesses);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        return map;
    }
}