package com.beardream.service;

import com.beardream.dao.BusinessTypeMapper;
import com.beardream.model.BusinessBusinessTypeTag;
import com.beardream.model.BusinessType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/17.
 */
@Component
@Service
public class BusinessTypeService {
    @Autowired
    public BusinessTypeMapper mBusinessTypeMapper;





    public int deleteTag(BusinessType businessType) {
        return mBusinessTypeMapper.deleteByPrimaryKey(businessType.getBusinessTypeId());
    }

    public Map getPage(BusinessType businessType, int pageNum, int pageSize){
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<BusinessType> businessTypes =mBusinessTypeMapper.findBySelective(new BusinessType());
        PageInfo page = new PageInfo(businessTypes);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        return map;
    }
}
