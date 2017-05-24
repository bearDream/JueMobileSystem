package com.beardream.Controller;

import com.beardream.Utils.Constants;
import com.beardream.Utils.Json;
import com.beardream.Utils.ResultUtil;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.UserMapper;
import com.beardream.enums.ResultEnum;
import com.beardream.exception.UserException;
import com.beardream.model.Number;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.service.TakeNumService;
import io.swagger.annotations.Api;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by laxzh on 2017/5/6.
 * 取号控制器
 */
@RestController
@RequestMapping("/api/mobile/takeNum")
@Api(value = "取号服务",description = "提供RESTful风格API的取号的操作")
public class TakeNumController {

    private final static Logger mLogger = LoggerFactory.getLogger("TakeNumController.class");

    @Value("${push_message}")
    private String mPushMessage;

    @Autowired
    private TakeNumService mTakeNumService;

    @Autowired
    private WxMpService mWxMpService;

    @Autowired
    private UserMapper mUserMapper;

    // 取号
    @GetMapping("/takeNum")
    public Result takeNum(Number number, HttpSession session){
        if (session.getAttribute(Constants.USER) == null){
            //未登录
            throw new UserException(ResultEnum.Logout);
        }
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);

        if (!TextUtil.isEmpty(number.getBusinessId()) && !TextUtil.isEmpty(number.getPeopleNum())){
            return ResultUtil.error(-1,"请选择商家并填写人数后又提交");
        }
        number.setUserId(user.getUserId());
        number.setIsExpired((byte) 1); // 设置为没有过期
        Result num = mTakeNumService.takeNum(number);
        if (num.getCode() != -1)
            return ResultUtil.success(num.getData());
        else
            return ResultUtil.error(-1,num.getMsg());
    }

    // 获取用户所取到对应商家的号  eg:用户已取号，获取他的取号码
    @GetMapping("/getNum")
    public Result getNum(Number number, HttpSession session){
        if (session.getAttribute(Constants.USER) == null){
            //未登录
            throw new UserException(ResultEnum.Logout);
        }
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);

        if (!TextUtil.isEmpty(number.getBusinessId()) && !TextUtil.isEmpty(number.getPeopleNum())){
            return ResultUtil.error(-1,"请选择商家并填写人数后又提交");
        }

        number.setUserId(user.getUserId());
        number.setIsExpired((byte) 1);
        Result num = mTakeNumService.getCurrentNum(number);

        if (num.getCode() == -1)
            return ResultUtil.error(-1,num.getMsg());
        else
            return ResultUtil.success(num.getData());
    }

    // 获取当前排到第几号   eg:用户已取号，获取当前排到第几号
    @GetMapping("/getCurrent")
    public Result getCurrentNum(Number number, HttpSession session){

        if (!TextUtil.isEmpty(number.getBusinessId())){
            return ResultUtil.error(-1,"请选择商家后又提交");
        }

        number.setIsExpired((byte) 1);
        Result num = mTakeNumService.refreshNum(number);

        if (num.getCode() == -1)
            return ResultUtil.error(-1,num.getMsg());
        else
            return ResultUtil.success(num.getData());
    }

    // 叫号 （商家调用）  eg:商家将推送消息给当前号
    @GetMapping("/callNum")
    public Result callNum(Number number, HttpSession session){

        // 传过来一个商家id和number，将该条记录is_expire设置为 0
        if (number != null && !TextUtil.isEmpty(number.getBusinessId()) && !TextUtil.isEmpty(number.getNumber())){
            return ResultUtil.error(-1,"请选择商家并携带number后又提交");
        }

        // 根据number，businessId，is_expire来查找被叫号的顾客
        Result result = mTakeNumService.callNum(number);

        if (result.getCode() == -1) {
            return ResultUtil.error(-1,result.getMsg());
        }else {
            // 对过号（被叫号的）顾客进行消息推送
            User user = (User) result.getData();
            pushMsg(number.getNumber(), number.getNumber(), user.getUserId());
            return ResultUtil.success(result.getMsg());
        }
    }

    // 过号 （商家调用）  设置这个号过期，并推送消息给剩下的所有号，通知他们队列已更新
    @GetMapping("/passNum")
    public Result passNum(Number number, HttpSession session){
        // 传过来一个商家id和number，将该条记录is_expire设置为 0
        if (number != null && !TextUtil.isEmpty(number.getBusinessId()) && !TextUtil.isEmpty(number.getNumber())){
            return ResultUtil.error(-1,"请选择商家并携带number后又提交");
        }

        Result result = mTakeNumService.passNum(number);

        if (result.getCode() == -1) {
            return ResultUtil.error(-1,result.getMsg());
        }else {
            // 当过了一个号时，应该取得剩下所有号的集合，集合中装的信息有（number,openid，currentNum）
            List<Number> remainLists = (List<Number>) result.getData();
            if (remainLists.size() == 0){
                // 说明后面没有号了,不需要推送消息
                return ResultUtil.success("设置成功");
            }
            // 设置当前号是队列的第一个元素（因为队列已经过排序，因此第一个就是队列中对前面的号）
            int curretNum = remainLists.get(0).getNumId();

            // 对队列中的其他号进行推送
            for (Number remainList : remainLists) {
                pushMsg(remainList.getNumId(), curretNum, remainList.getUserId());
            }

            return ResultUtil.success(result.getMsg());
        }
    }

    // 微信推送消息过号
    public void pushMsg(int takeNum, int CurrentNum, int userId){

        // 根据userId查找openid
        User user = mUserMapper.selectByPrimaryKey(userId);

        if (!TextUtil.isEmpty(user.getOpenid())){
            mLogger.error("推送微信消息失败,原因是={}","用户openid为空");
            return;
        }

        StringBuilder text = new StringBuilder();
        text.append("亲~ 您当前所取到的号是： ");
        text.append(takeNum);
        text.append(" 号，当前排到 ");
        text.append(CurrentNum);
        text.append(" 号,在你前面还有 ");
        text.append(takeNum - CurrentNum);
        text.append(" 桌");

        WxMpKefuMessage message = WxMpKefuMessage.TEXT().toUser(user.getOpenid()).content(text.toString()).build();

        try {
            mWxMpService.getKefuService().sendKefuMessage(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
            mLogger.error("推送微信消息失败,原因是={}",e.getMessage());
        }
    }
}
