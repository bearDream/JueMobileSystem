package com.beardream.Controller;

import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.RoleMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.*;
import com.beardream.service.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by laxzh on 2017/5/6.
 * 商家控制器
 */
@RestController
@RequestMapping("/api/mobile/business")
@Api(value = "商家服务",description = "提供RESTful风格API的商家的增删改查服务")
public class BuisnessController {
    @Autowired
    private  BusinessMapper businessMapper;

    @Autowired
    private BusinessService mBusinessService;

    @ApiOperation("获取单个商家信息")
    @GetMapping
    public Result get(Business
                                  business, BindingResult bindingResult) {
            System.out.println(business.getBusinessId());
            return ResultUtil.success(businessMapper.findBySelective(business));
        }

    @ApiOperation("添加商家")
    @PostMapping
    public Result post(Business business){
        int result;
        if (business == null)
            return ResultUtil.error(-1,"没有参数");
        List<Business> b = businessMapper.findBySelective(business);
        if (b.size()>0)
            return  ResultUtil.error(-1,"添加失败，商家已存在");
        business.setAddTime(new Date());
        result = businessMapper.insertSelective(business);
        if (result > 0 )
            return  ResultUtil.success("添加成功");
        else
            return  ResultUtil.error(-1,"添加失败");
    }

    @ApiOperation("删除商家")
    @DeleteMapping
    public Result delete(Business business){
        int result;
        result = businessMapper.deleteByPrimaryKey(business.getBusinessId());
        if (result>0)
            return ResultUtil.success("删除成功");
        else
            return ResultUtil.error(-1,"删除失败");
    }

    @ApiOperation("更新商家")
    @PutMapping
    public Result put(Business business){
        int result;
        System.out.println(business.getBusinessId());
        business.setAddTime(new Date());
        result = businessMapper.updateByPrimaryKeySelective(business);
        if (result>0)
            return ResultUtil.success("修改成功");
        else
            return  ResultUtil.error(-1,"修改失败");
    }

    // 根据排号桌数最多和最少排序  2、将所有商家查询出来  3、查询附近的商家
    @ApiOperation("分页查询商家推荐,根据不同条件进行查询")
    @GetMapping("/recommend")
    public Result getPage(Business business, Tag tag,Dish dish,
                          @RequestParam(value = "waitSort", defaultValue = "desc", required = false)  String waitSort,
                          @RequestParam(value = "pageNum", defaultValue = "1",required = false)  int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10",required = false)  int pageSize,
                          BindingResult bindingResult){
        if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)){
            return ResultUtil.error(-1,"pageNum,pageNum不能为空！");
        }

        // waitSort为1则按照等待人数从多到少排序，为0则按照从少到多排序,并且查出来的商家都是开通取号功能的
        // 1、先从取号表 连接查询 商家表 中查询出所有 开通取号的商家信息，装在集合中
        business.setIsTake((byte) 1);
        Map businessTakeList = mBusinessService.getPage(business, pageNum, pageSize);

        // 2、将开通取号功能的商家进行迭代查询每个商家的等待桌数状态,再将集合按照等待人数多少进行排序
        List<Business> businessList = mBusinessService.getBusinessTakeInfoSort(businessTakeList, waitSort);

        return ResultUtil.success(businessList);

    }
}
