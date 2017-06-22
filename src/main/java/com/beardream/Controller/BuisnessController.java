package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.RoleMapper;
import com.beardream.dao.UserMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.*;
import com.beardream.model.Number;
import com.beardream.service.BusinessService;
import com.beardream.service.CollectionService;
import com.beardream.service.CommonService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private CollectionService mCollectionService;

    @Autowired
    private BusinessService mBusinessService;

    @Autowired
    private CommonService mCommonService;

    @ApiOperation("获取单个商家信息")
    @GetMapping
    public Result get(Business business,
                      @Param(value = "userLontitude") Double lontitude,
                      @Param(value = "userLatitude") Double latitude,
                      HttpSession session,
                      BindingResult bindingResult) {
        // 获取到商家的基本信息和该商家的排队情况
        // 商家信息（包括是否开放了取号功能） + 排队的队列（有效的）
        System.out.println(business.getBusinessId());
        Result businessResult = mBusinessService.find(business);
        if (businessResult.getCode() == -1)
            return businessResult;

        // 将查询出来的商家信息给business1
        Business business1 = (Business) businessResult.getData();
        if (lontitude != null && latitude != null)
            business1 = (Business) mBusinessService.Distance(business1, lontitude, latitude).getData();

        // 如果是用户查看该商家的情况，则还应该拿到他是否收藏了该商家的信息
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        if (user != null) {
            // 根据用户id， dish_business_id查询是否收藏过
            UserCollection userCollection = new UserCollection(user.getUserId(), business1.getBusinessId(), 3);
            userCollection = mCollectionService.queryBusinessDishCollect(userCollection);
            business1.setCollectionId(userCollection.getCollectionId());
        }

        // 根据business1的信息查询对应的队列信息（有效期的且是今天的）
        List<Number> businessQueue = mBusinessService.getBusinessQue(business1);

        // 1-4个人是小桌   5-6个人是中桌   7人以上为大桌
        Map kindTableMap = mBusinessService.getQueTableMap(businessQueue);

        // 将两个信息放到一个map中返回给前端
        Map<String, Object> resultMap = new HashedMap();

        resultMap.put("businessInfo", business1);
        resultMap.put("queue", kindTableMap);

        return ResultUtil.success(resultMap);
    }

    @ApiOperation("商家获取自己的信息")
    @GetMapping("/getUserBusiness")
    public Result getUserBusiness(Business business, HttpSession session, @RequestHeader HttpHeaders headers) {
        // 获取到商家的基本信息和该商家的排队情况
        // 商家信息（包括是否开放了取号功能） + 排队的队列（有效的）
        User user = null;
        List headList = headers.get("thirdSession");
        String thirdSession = String.valueOf(headList.get(0));
        System.out.println(thirdSession);
        if (thirdSession != null){
            Result res = mCommonService.getUserInfoByThirdSession((Map) session.getAttribute(thirdSession));
            if(res.getCode() == -1){
                return res;
            }
            user = (User) res.getData();
        }else {
            user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        }
        business.setUserId(user.getUserId());
        Result businessResult = mBusinessService.find(business);

        if (businessResult.getCode() == -1)
            return businessResult;

        // 将查询出来的商家信息给business1
        Business business1 = (Business) businessResult.getData();

        // 根据business1的信息查询对应的队列信息（有效期的且是今天的）
        List<Number> businessQueue = mBusinessService.getBusinessQue(business1);

        // 1-4个人是小桌   5-6个人是中桌   7人以上为大桌
        Map kindTableMap = mBusinessService.getQueTableMap(businessQueue);

        // 将两个信息放到一个map中返回给前端
        Map<String, Object> resultMap = new HashedMap();

        resultMap.put("business", business1);
        resultMap.put("queue", kindTableMap);

        return ResultUtil.success(resultMap);
    }

    @ApiOperation("添加商家")
    @PostMapping
    public Result post(Business business,
                       @RequestHeader HttpHeaders headers,
                       HttpSession session){
        User user = null;
        List headList = headers.get("thirdSession");
        String thirdSession = String.valueOf(headList.get(0));
        if (thirdSession != null){
            Result res = mCommonService.getUserInfoByThirdSession((Map) session.getAttribute(thirdSession));
            if(res.getCode() == -1){
                return res;
            }
            user = (User) res.getData();
        }else {
            user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        }


        int result;
        if (business == null)
            return ResultUtil.error(-1,"没有参数");
        List<Business> b = businessMapper.findBySelective(business);
        if (b.size()>0)
            return  ResultUtil.error(-1,"添加失败，商家已存在");
        business.setAddTime(new Date());
        business.setUserId(user.getUserId());
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
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Result put(@RequestBody Business business){
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
                          @RequestParam(value = "latitude", defaultValue = "24.972967837", required = false)  double latitude,
                          @RequestParam(value = "longtitude", defaultValue = "102.6507997513", required = false)  double longtitude,
                          @RequestParam(value = "waitSort", defaultValue = "desc", required = false)  String waitSort,
                          @RequestParam(value = "pageNum", defaultValue = "1",required = false)  int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10",required = false)  int pageSize,
                          BindingResult bindingResult){
        if (!TextUtil.isEmpty(pageNum) || !TextUtil.isEmpty(pageSize)){
            return ResultUtil.error(-1,"pageNum,pageNum不能为空！");
        }

        // 获取取号界面的三个list，将三个list装到一个map中，1、按照距离排序  2、按照星级level排序  3、按照取号桌数排序
        // waitSort为1则按照等待人数从多到少排序，为0则按照从少到多排序,并且查出来的商家都是开通取号功能的

        Map<String, Object> businessMap = new HashedMap();// 装三个list集合
        business.setIsShow((byte)1);

        // 一、查询按照距离排序的商家
        Map businessLocationMap = mBusinessService.getPage(business, pageNum, pageSize);
        Map businessDistanceList = mBusinessService.getBusinessLocationSort(businessLocationMap, latitude, longtitude, waitSort);


        // 二、查询按照星级level排序的商家
        Map businessLevelMap = mBusinessService.getPage(business, pageNum, pageSize);
        Map businessLevelList = mBusinessService.getBusinessLevelInfoSort(businessLevelMap, "desc");


        // 三、查询按照取号桌数排序的商家
        // 1、先从取号表 连接查询 商家表 中查询出所有 开通取号的商家信息，装在集合中
        business.setIsTake((byte) 0);
        Map businessTakeMap = mBusinessService.getPage(business, pageNum, pageSize);
        // 2、将开通取号功能的商家进行迭代查询每个商家的等待桌数状态,再将集合按照等待人数多少进行排序
        Map businessTakeList = mBusinessService.getBusinessTakeInfoSort(businessTakeMap, waitSort);

        businessMap.put("distanceList", businessDistanceList);
        businessMap.put("levelList", businessLevelList);
        businessMap.put("takeList", businessTakeList);

        return ResultUtil.success(businessMap);
    }


    // 微信端一级页面获取商家信息（一个商家图片带两个商家菜品的形式）
    @ApiOperation("分页查询一级页面的商家推荐，隐式根据用户的body_status来查找对应的数据")
    @GetMapping("/topBusiness")
    public Result getTopPageBusiness(Business business,
                                     HttpSession session,
                                     @RequestParam(value = "pageNum", defaultValue = "1",required = false)  int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10",required = false)  int pageSize){
        // 0、获取用户信息，根据他的body_status来推荐商家的两个菜品
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);

        // 1、先查询出商家来
        business.setIsShow((byte)1);
        Map businessMap = mBusinessService.getPage(business, pageNum, pageSize);
        // 2、然后根据商家list遍历查询商家的两个菜品
        Map businessDishMap = mBusinessService.getBusinessDishList(businessMap, user.getBodyStatus());

        if (businessDishMap.get("page") != null)
            return ResultUtil.success(businessDishMap);
        return ResultUtil.error(-1,"查询失败");
    }

    public Result getBusiness(BusinessDishTag businessDishTag){
        if (!TextUtil.isEmpty(businessDishTag.getBusinessId())){
            return ResultUtil.error(-1,"商家ID不能为空");
        }
        if (mBusinessService.get(businessDishTag)!=null){
            return  ResultUtil.success(mBusinessService.get(businessDishTag));
        }
        else {
            return  ResultUtil.error(-1,"商家不存在");
        }
    }
}
