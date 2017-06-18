package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
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

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private CollectionService mCollectionService;

    /*
    Put更新数据的请求只能是参数形式，不能写在body中
     */


    @ApiOperation("删除收藏")
    @DeleteMapping
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
    public @ResponseBody Result post(@RequestBody UserCollection userCollection, HttpSession session) {
        return mCollectionService.add(userCollection, session);
    }


    //需要分页
    // 需要两个参数： 当前所在页pageSize 需要几条数据limit
    @ApiOperation("分页获取用户收藏信息，根据不同的类别放在不同的集合中，最后装到一个大map中返回")
    @GetMapping
    public Result getPage(UserCollection userCollection,
                          HttpSession session,
                          @RequestParam(value = "collectionType", defaultValue = "1", required = false) int collectionType,
                          @RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        userCollection.setUserId(user.getUserId());
        userCollection.setCollectionType(collectionType);
        // 根据不同的类型拿不同的收藏集合  1：菜品  2：商家 3.文章
        List<UserCollection> collectionList = null;
        try {
            collectionList = mCollectionService.getCollectionList(userCollection, pageNum, pageSize);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultUtil.error(-1,"转码失败");
        }
        if (collectionList.size() > 0){
            return ResultUtil.success(collectionList);
        }
        return ResultUtil.error(-1,"用户还没有收藏哟~");
    }

}
