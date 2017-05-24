package com.beardream.model;

import java.util.Date;

/**
 * Created by soft01 on 2017/5/24.
 */
public class DishBusinessTag {
    //菜品表字段
    private Integer dishId;
    private String dishName;
    private String dishImage;
    private String dishContent;
    private String dishDesc;
    private Date dishAddTime;

    //商家表字段
    private Integer businessId;
    private Integer userId;
    private String businessName;
    private String address;
    private  Integer tel;
    private String businessContent;
    private String businessImage;
    private String businessCarouselImage;
    private Byte isShow;
    private  Byte isTake;
    private Date businessAddTime;

    //标签字段
    private Integer tagId;
    private String tagName;
    private String tagContent;
    private  Byte tagType;
    private Date tagAddTime;

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getDishContent() {
        return dishContent;
    }

    public void setDishContent(String dishContent) {
        this.dishContent = dishContent;
    }

    public String getDishDesc() {
        return dishDesc;
    }

    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc;
    }

    public Date getDishAddTime() {
        return dishAddTime;
    }

    public void setDishAddTime(Date dishAddTime) {
        this.dishAddTime = dishAddTime;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getBusinessContent() {
        return businessContent;
    }

    public void setBusinessContent(String businessContent) {
        this.businessContent = businessContent;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(String businessImage) {
        this.businessImage = businessImage;
    }

    public String getBusinessCarouselImage() {
        return businessCarouselImage;
    }

    public void setBusinessCarouselImage(String businessCarouselImage) {
        this.businessCarouselImage = businessCarouselImage;
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    public Byte getIsTake() {
        return isTake;
    }

    public void setIsTake(Byte isTake) {
        this.isTake = isTake;
    }

    public Date getBusinessAddTime() {
        return businessAddTime;
    }

    public void setBusinessAddTime(Date businessAddTime) {
        this.businessAddTime = businessAddTime;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    public Byte getTagType() {
        return tagType;
    }

    public void setTagType(Byte tagType) {
        this.tagType = tagType;
    }

    public Date getTagAddTime() {
        return tagAddTime;
    }

    public void setTagAddTime(Date tagAddTime) {
        this.tagAddTime = tagAddTime;
    }
}
