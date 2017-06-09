package com.beardream.Controller;

import com.beardream.Utils.*;
import com.beardream.dao.DishBusinessMapper;
import com.beardream.dao.OrderMapper;
import com.beardream.model.DoubleDishBusiness;
import com.beardream.model.Order;
import com.beardream.model.Result;
import com.beardream.model.User;
import com.beardream.service.DishBusinessService;
import com.beardream.service.OrderService;
import com.github.binarywang.wxpay.bean.WxPayOrderNotifyResponse;
import com.github.binarywang.wxpay.bean.request.WxPayBaseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/27.
 */
@RestController
@RequestMapping("/api/mobile/order")
@Api(value = "订单服务",description = "提供RESTful风格API的订单服务")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "wxPayService")
    private WxPayService mWxPayService;

    @Autowired
    private OrderService mOrderService;

    @Value("${domain}")
    private String domain;

    @PostMapping
    @ApiOperation("订单入库")
    public @ResponseBody Result getDishBusiness(@RequestBody Order order, HttpSession session, HttpServletRequest httpRequest) {
        User user = Json.fromJson((String) session.getAttribute(Constants.USER), User.class);
        order.setUserId(user.getUserId());
        order.setAddTime(new Date());
        Result result = null;
        if (!TextUtil.isEmpty(order.getOrderId())){
            String orderNo = "";
            orderNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            orderNo += user.getUserId();
            System.out.println("订单号是: " + orderNo);
            order.setOrderId(orderNo);
            result = mOrderService.order(order);
        }else {
            order.setOrderStatus((byte) 0);
            result = mOrderService.update(order);
        }

        // 若订单入库成功，则调用微信api调用统一下单
        if (result.getCode() != -1){
            WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
            Date now = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(now);
            calendar.add(calendar.DATE, 1);
            String startTime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
            String expireTime = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());

            String ip = Ip.getRemoteIp(httpRequest);
            if (TextUtil.isBlank(ip)) {
                ip = "127.0.0.1";
            }else{
                if (ip.contains(",")) {
                    ip=ip.split(",")[0];
                }
            }
            request.setSpbillCreateIp(ip);
            request.setBody("蕨菜美食订单");
            request.setOpenid(user.getOpenid());
            request.setOutTradeNo(order.getOrderId());
            request.setTotalFee(WxPayBaseRequest.yuanToFee(String.valueOf(order.getOrderPice())));
            request.setTimeStart(startTime);
            request.setTimeExpire(expireTime);
            request.setNotifyURL(domain + "/api/mobile/order/payNotify");
            request.setTradeType("JSAPI");

            try {
                // 该方法会自动调用统一下单方法，不需要手动执行
                Map jsApiParams = mWxPayService.getPayInfo(request);
                String prepayId = (String) jsApiParams.get("package").toString().split("=")[1];
                order.setPrepayId(prepayId);
                mOrderService.update(order);
                return ResultUtil.success(jsApiParams);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
            return ResultUtil.success(request);
        }else
            return ResultUtil.error(-1,"订单入库失败，请稍后重新尝试支付");
    }

    /*
        用户支付成功后，微信回调NotifyURL会访问该方法用以处理订单状态
     */
    @ResponseBody
    @RequestMapping("/payNotify")
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        String xmlResult = mOrderService.payNotify(request, response);
        try {
            System.out.println("支付回调xml结果");
            System.out.println(xmlResult);
            return xmlResult;
        } catch (Exception e) {
            logger.error("微信回调结果异常,异常原因{}", e.getMessage());
            return xmlResult;
        }
    }

    /*
        支付成功后，根据prepayId查询用户订单信息
     */
    @GetMapping(value = "/{prepayId}")
    public Result queryOrder(@PathVariable String prepayId) {
        if (!TextUtil.isEmpty(prepayId))
            return ResultUtil.error(-1,"预支付订单id不能为空");

        return mOrderService.queryOrder(prepayId);
    }

    /*
        查询订单详细信息，商家信息，菜品信息以及订单状态，三个信息装在一个map中返回前端
     */
    @GetMapping(value = "/get")
    public Result queryOrderInfo(@Param(value = "orderId") String orderId) {
        if (!TextUtil.isEmpty(orderId))
            return ResultUtil.error(-1,"订单id不能为空");

        return mOrderService.queryOrderInfo(orderId);
    }
}

