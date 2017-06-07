package com.beardream.service;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.OrderMapper;
import com.beardream.model.Order;
import com.beardream.model.Result;
import com.github.binarywang.wxpay.bean.WxPayOrderNotifyResponse;
import com.github.binarywang.wxpay.bean.result.WxPayBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by soft01 on 2017/5/24.
 */
@Component
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderMapper mOrderMapper;

    @Resource(name = "wxPayService")
    private WxPayService mWxPayService;

    public Result order(Order order){
        order.setOrderStatus((byte) 0);
        int res = mOrderMapper.insertSelective(order);
        if (res > 0)
            return ResultUtil.success("订单已入库");
        return ResultUtil.error(-1,"订单入库失败");
    }

    public Result update(Order order){
        int result = mOrderMapper.updateByPrimaryKeySelective(order);
        if (result > 0)
            return ResultUtil.success("更新成功");
        return ResultUtil.error(-1,"更新失败");
    }

    public String payNotify(HttpServletRequest request, HttpServletResponse response){
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = mWxPayService.getOrderNotifyResult(xmlResult);
            System.out.println(result.toString());
            // 结果正确
            String orderId = result.getOutTradeNo();
            String tradeNo = result.getTransactionId();
            String totalFee = WxPayBaseResult.feeToYuan(result.getTotalFee());

            // 查询支付的订单
            WxPayOrderQueryResult result1 = mWxPayService.queryOrder(tradeNo, orderId);
            Order order = new Order();
            order.setOrderId(orderId);
            order.setWeixingTranid(tradeNo);
            order.setOrderPice(Float.valueOf(totalFee));
            order.setOpenid(result1.getOpenid());
            order.setTranTime(new Date());
            order.setOrderStatus((byte) 1);
            int res = mOrderMapper.updateByPrimaryKeySelective(order);

            //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            if (res > 0)
                return WxPayOrderNotifyResponse.success("处理成功!");
            else
                return WxPayOrderNotifyResponse.fail("订单更新失败");
        } catch (Exception e) {
            logger.error("微信回调结果异常,异常原因{}", e.getMessage());
            return WxPayOrderNotifyResponse.fail(e.getMessage());
        }
    }
}
