package com.beardream.model;

import java.util.Date;

public class Business {
    private Integer businessId;

    private Integer userId;

    private String name;

    private String address;

    private String tel;

    private String businessImage;

    private String businessCarouselImage;

    private Byte isShow;

    private Byte isTake;

    private Date addTime;

    private String content;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(String businessImage) {
        this.businessImage = businessImage == null ? null : businessImage.trim();
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getBusinessCarouselImage() {
        return businessCarouselImage;
    }

    public void setBusinessCarouselImage(String businessCarouselImage) {
        this.businessCarouselImage = businessCarouselImage;
    }

    public Byte getIsTake() {
        return isTake;
    }

    public void setIsTake(Byte isTake) {
        this.isTake = isTake;
    }
}