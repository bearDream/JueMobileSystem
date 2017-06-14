package com.beardream.model;

import java.util.Date;

public class Order {
    private String orderId;

    private Integer userId;

    private String dishId;

    private Date addTime;

    private Double orderPice;

    private String remark;

    private Byte orderStatus;

    private String weixingTranid;

    private Date tranTime;

    private Double tranMoney;

    private String prepayId;

    private String openid;

    private Integer businessId;

    private Integer evaluateId;

    public Order() {}

    public Order(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Double getOrderPice() {
        return orderPice;
    }

    public void setOrderPice(Double orderPice) {
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

    public Double getTranMoney() {
        return tranMoney;
    }

    public void setTranMoney(Double tranMoney) {
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