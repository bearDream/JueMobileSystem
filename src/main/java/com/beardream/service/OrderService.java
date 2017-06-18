package com.beardream.service;

import com.beardream.Utils.ResultUtil;
import com.beardream.dao.BusinessMapper;
import com.beardream.dao.DishBusinessMapper;
import com.beardream.dao.DishMapper;
import com.beardream.dao.OrderMapper;
import com.beardream.model.*;
import com.github.binarywang.wxpay.bean.WxPayOrderNotifyResponse;
import com.github.binarywang.wxpay.bean.result.WxPayBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by soft01 on 2017/5/24.
 */
@Component
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderMapper mOrderMapper;

    @Autowired
    private DishBusinessMapper mDishBusinessMapper;

    @Autowired
    private BusinessMapper mBusinessMapper;

    @Resource(name = "wxPayService")
    private WxPayService mWxPayService;

    public Order queryByOrderId(String orderId){
        return mOrderMapper.selectByPrimaryKey(orderId);
    }

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

    // 查询用户的所有订单
    public List queryAllOrders(User user){
        Order order = new Order(user.getUserId());
        List<Order> orders = mOrderMapper.findBySelective(order);
        return orders;
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
            WxPayOrderQueryResult result1 = mWxPayService.queryOrder(tradeNo, null);
            System.out.println("支付成功后的orderId: " + result.getOutTradeNo());
            Order order = new Order();
            order.setOrderId(orderId);
            order.setWeixingTranid(tradeNo);
            order.setOrderPice(Double.valueOf(totalFee));
            order.setOpenid(result1.getOpenid());
            order.setTranTime(new Date());
            order.setOrderStatus((byte) 1);
            int res = mOrderMapper.updateByPrimaryKeySelective(order);

            //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            return WxPayOrderNotifyResponse.success("处理成功!");
        } catch (Exception e) {
            logger.error("微信回调结果异常,异常原因{}", e.getMessage());
            return WxPayOrderNotifyResponse.fail(e.getMessage());
        }
    }

    public Result queryOrder(String prepayId) {
        Order order = new Order();
        order.setPrepayId(prepayId);
        List<Order> orders = mOrderMapper.findBySelective(order);
        if (orders.size() == 1) {
            return ResultUtil.success(orders.get(0));
        }
        return ResultUtil.error(-1,"订单不存在");
    }

    public Result queryOrderInfo(String orderId) {
        // 1、先查询订单的信息
        Order order = mOrderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ResultUtil.error(-1,"订单信息不存在");

        // 2、在分割dishId查询菜品的信息，装到一个list中
        String[] dishArr = order.getDishId().split(",");
        List<DishBusiness> dishList = new ArrayList<>();
        for (String s : dishArr) {
            dishList.add(mDishBusinessMapper.findDishBusinessBySelective(Integer.valueOf(s)));
        }

        // 3、再查询商家信息
        Business business = mBusinessMapper.selectByPrimaryKey(order.getBusinessId());
        if (business == null)
            return ResultUtil.error(-1,"商家信息不存在");

        // 4、将三组信息装到一个map中
        Map map = new HashedMap();
        map.put("order", order);
        map.put("dishList", dishList);
        map.put("business", business);
        return ResultUtil.success(map);
    }

    public Result checked(String orderId) {
        Order order = new Order(orderId, (byte) 2);

        int res = mOrderMapper.updateByPrimaryKeySelective(order);
        if (res == 1)
            return ResultUtil.success("更新成功");
        return ResultUtil.error(-1,"更新失败");
    }
}
