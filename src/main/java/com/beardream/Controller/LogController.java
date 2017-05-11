package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.LogMapper;
import com.beardream.dao.RoleMapper;
import com.beardream.ioc.Log;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.Result;
import com.beardream.model.Role;
import com.beardream.service.LogService;
import com.beardream.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 * 日志控制器
 */
@RestController
@RequestMapping("/log")
@Api(value = "日志服务",description = "提供RESTful风格API的日志的增删改查服务")
@PermissionModule(text = "日志管理")
public class LogController {

    @Autowired
    private LogService mLogService;

    /*
    Put更新数据的请求只能是参数形式，不能写在body中
     */
    @ApiOperation("删除日志")
    @DeleteMapping
    @Log
    public Result delete(com.beardream.model.Log log){
        int result;
        result = mLogService.deleteLog(log);
        if (result > 0)
            return ResultUtil.success("删除成功");
        else
            return ResultUtil.success("删除失败");
    }

    //需要分页
    // 需要两个参数： 当前所在页pageSize 需要几条数据limit
    @ApiOperation("分页获取角色")
    @GetMapping("/getpage")
    @Log
    public Result getPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false)  int pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false)  int pageSize){
//        System.out.println(role.getRoleId());
        System.out.println(pageNum);
        System.out.println(pageSize);
        if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)){
            return ResultUtil.error(-1,"pageNum,pageNum不能为空！");
        }
        return ResultUtil.success(mLogService.getPage(pageNum, pageSize));
    }
}
