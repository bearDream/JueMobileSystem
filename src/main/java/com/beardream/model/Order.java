package com.beardream.model;

import java.util.Date;

public class Order {
    private Integer orderId;

    private Integer userId;

    private Integer dishId;

    private Date addTime;

    private Float orderPice;

    private String remark;

    private Byte orderStatus;

    private String weixingTranid;

    private Date tranTime;

    private Date tranMoney;

    private String prepayId;

    private String openid;

    private Integer businessId;

    private Integer evaluateId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Float getOrderPice() {
        return orderPice;
    }

    public void setOrderPice(Float orderPice) {
        this.orderPice = orderPice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getWeixingTranid() {
        return weixingTranid;
    }

    public void setWeixingTranid(String weixingTranid) {
        this.weixingTranid = weixingTranid == null ? null : weixingTranid.trim();
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Date getTranMoney() {
        return tranMoney;
    }

    public void setTranMoney(Date tranMoney) {
        this.tranMoney = tranMoney;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId == null ? null : prepayId.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Integer evaluateId) {
        this.evaluateId = evaluateId;
    }
}