package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.UserCollectionMapper;
import com.beardream.ioc.Log;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.*;
import com.beardream.service.CollectionService;
import com.beardream.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 * 收藏控制器
 */
@RestController
@RequestMapping("/api/mobile/collection")
@Api(value = "收藏服务",description = "提供RESTful风格API的收藏的增删改查服务")
public class CollectionController {

    @Autowired
    private UserCollectionMapper mUserCollectionMapper;

    /*
    Put更新数据的请求只能是参数形式，不能写在body中
     */


    @ApiOperation("删除收藏")
    @DeleteMapping
    @Log
    public Result delete(UserCollection userCollection) {
        int result;
        result = mUserCollectionMapper.deleteByPrimaryKey(userCollection.getCollectionId());
        if (result > 0)
            return ResultUtil.success("删除收藏成功");
        else
            return ResultUtil.error(-1, "删除收藏失败");
    }

    @ApiOperation("添加收藏")
    @PostMapping
    public Result post(UserCollection userCollection) {
        int result;
        List<UserCollection> u = mUserCollectionMapper.findBySelective(userCollection);
        if (u.size() > 0)
            return ResultUtil.error(-1, "添加失败，收藏已存在");
        userCollection.setAddTime(new Date());
        result = mUserCollectionMapper.insertSelective(userCollection);
        if (result > 0)
            return ResultUtil.success("添加成功");
        else
            return ResultUtil.error(-1, "添加失败");
    }


    //需要分页
    // 需要两个参数： 当前所在页pageSize 需要几条数据limit
    @ApiOperation("分页获取角色")
    @GetMapping("/getpage")
    @Log
    public Result getPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        System.out.println(role.getRoleId());
        System.out.println(pageNum);
        System.out.println(pageSize);
        if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)) {
            return ResultUtil.error(-1, "pageNum,pageNum不能为空！");
        }
        PageHelper.startPage(pageNum , pageSize).setOrderBy("add_time asc");
        List<UserCollection> userCollections =mUserCollectionMapper.findBySelective(new UserCollection());
        PageInfo page = new PageInfo(userCollections);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("list",userCollections);
        return ResultUtil.success(map);
    }

}
