package com.beardream.model;

import java.util.Date;

public class DishBusiness {
    private Integer dishBusinessId;

    private Integer dishId;

    private Integer businessId;

    private String dishType;

    private Boolean isFavorable;

    private Boolean isShow;

    private Boolean isTop;

    private Integer orderCount;

    private Float dishPrice;

    private Float favorablePrice;

    private Date addtime;

    public Integer getDishBusinessId() {
        return dishBusinessId;
    }

    public void setDishBusinessId(Integer dishBusinessId) {
        this.dishBusinessId = dishBusinessId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType == null ? null : dishType.trim();
    }

    public Boolean getIsFavorable() {
        return isFavorable;
    }

    public void setIsFavorable(Boolean isFavorable) {
        this.isFavorable = isFavorable;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Float getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Float dishPrice) {
        this.dishPrice = dishPrice;
    }

    public Float getFavorablePrice() {
        return favorablePrice;
    }

    public void setFavorablePrice(Float favorablePrice) {
        this.favorablePrice = favorablePrice;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}