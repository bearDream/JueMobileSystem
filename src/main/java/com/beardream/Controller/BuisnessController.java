package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.RoleMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.*;
import com.beardream.model.Number;
import com.beardream.service.BusinessService;
import com.beardream.service.TakeNumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by laxzh on 2017/5/6.
 * 商家控制器
 */
@RestController
@RequestMapping("/api/mobile/business")
@Api(value = "商家服务",description = "提供RESTful风格API的商家的增删改查服务")
public class BuisnessController {

    @Autowired
    private  BusinessMapper mBusinessMapper;

    @Autowired
    private BusinessService mBusinessService;

    @Autowired
    private TakeNumService mTakeNumService;

    @ApiOperation("获取单个商家信息")
    @GetMapping
    public Result get(Business business, BindingResult bindingResult) {
            System.out.println(business.getBusinessId());
            return ResultUtil.success(mBusinessMapper.findBySelective(business));
    }

    @ApiOperation("判断用户是否注册过商家")
    @GetMapping("/getUserBusiness")
    public Result getUserBusiness(HttpSession session) {
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);

        Business business = new Business();
        business.setUserId(user.getUserId());

        Result businessResult = mBusinessService.find(business);

        // 该用户已注册商家，可以直接将商家信息返回给前端
        if (businessResult.getCode() != -1){

            // 还需要将商家当前排的号数，以及等待人数的数据返回回去
            BusinessNumber businessNumber = new BusinessNumber();
            businessNumber.setBusiness((Business) businessResult.getData());

            List<Number> queue = mTakeNumService.getBusinessNum(business);

            // 如果没有排队，则设置等待人数为0，当前号为0
            if (queue.size() == 0){
                businessNumber.setNumber(0);
                businessNumber.setWaitNumber(0);
                return ResultUtil.success(businessNumber);
            }

            // 队列的第一个元素即第一个号
            businessNumber.setNumber(queue.get(0).getNumber());
            // 队列长度即总排队人数
            businessNumber.setWaitNumber(queue.size());

            return ResultUtil.success(businessNumber);
        }
        // 返回code为-1，告诉前端用户没有注册，需要注册
        return businessResult;
    }

    @ApiOperation("添加商家")
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result post(@RequestBody Business business, HttpSession session){
        int result;
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        if (business == null)
            return ResultUtil.error(-1,"没有参数");
        if (!TextUtil.isEmpty(business.getName()) && !TextUtil.isEmpty(business.getTel()) && !TextUtil.isEmpty(business.getAddress()))
            return ResultUtil.error(-1,"请检查填写的商家信息是否正确");
        business.setUserId(user.getUserId());
        Result result1 = mBusinessService.add(business);
        if (result1.getCode() != -1)
            return  ResultUtil.success(result1.getData());
        else
            return result1;
    }

    @ApiOperation("删除商家")
    @DeleteMapping
    public Result delete(Business business){
        int result;
        result = mBusinessMapper.deleteByPrimaryKey(business.getBusinessId());
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
        result = mBusinessMapper.updateByPrimaryKeySelective(business);
        if (result>0)
            return ResultUtil.success("修改成功");
        else
            return  ResultUtil.error(-1,"修改失败");
    }
}
