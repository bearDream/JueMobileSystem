package com.beardream.service;

import com.beardream.dao.LogMapper;
import com.beardream.dao.UserMapper;
import com.beardream.model.Log;
import com.beardream.model.Role;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/10.
 */
@Component
@Service
public class LogService {

    @Autowired
    public LogMapper mLogMapper;


    public int deleteLog(Log log) {
        return mLogMapper.deleteByPrimaryKey(log.getLogId());
    }

    public Map getPage(int pageNum,int pageSize) {
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum , pageSize).setOrderBy("log_addtime asc");
        List<Log> logs =mLogMapper.findBySelective(new Log());
        PageInfo page = new PageInfo(logs);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
//        map.put("list",logs);
        return map;
    }
}
