package com.beardream.model;

import java.util.Date;
import java.util.List;

/**
 * Created by soft01 on 2017/5/25.
 */
public class BusinessDishTag {
    private Integer businessId;

    private Integer dishId;

    private Integer tagId;

    private String businessName;

    private String dishName;

    private String tagName;

    private List<Business> posts;

    private String businessAddress;

    private String businessTel;

    private  Date addTime;

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessTel() {
        return businessTel;
    }

    public void setBusinessTel(String businessTel) {
        this.businessTel = businessTel;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(String businessImage) {
        this.businessImage = businessImage;
    }

    private String businessImage;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public List<Business> getPosts() {
        return posts;
    }

    public void setPosts(List<Business> posts) {
        this.posts = posts;
    }
}
