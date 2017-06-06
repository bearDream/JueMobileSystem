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

    private Double longtitude;

    private Double latitude;

    // 商家带的两个菜品的属性
    private Integer OneDishId;

    private String OneDishName;

    private String OneDishRecImage;

    private Integer TwoDishId;

    private String TwoDishName;

    private String TwoDishRecImage;

    // 该商家等待人数
    private Integer wait;

    // 该商家距离用户的距离
    private Double distance;

    // 有该菜的商家数量
    private Integer businessNum;

    public Integer getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(Integer businessNum) {
        this.businessNum = businessNum;
    }

    public Integer getOneDishId() {
        return OneDishId;
    }

    public void setOneDishId(Integer oneDishId) {
        OneDishId = oneDishId;
    }

    public String getOneDishName() {
        return OneDishName;
    }

    public void setOneDishName(String oneDishName) {
        OneDishName = oneDishName;
    }

    public String getOneDishRecImage() {
        return OneDishRecImage;
    }

    public void setOneDishRecImage(String oneDishRecImage) {
        OneDishRecImage = oneDishRecImage;
    }

    public Integer getTwoDishId() {
        return TwoDishId;
    }

    public void setTwoDishId(Integer twoDishId) {
        TwoDishId = twoDishId;
    }

    public String getTwoDishName() {
        return TwoDishName;
    }

    public void setTwoDishName(String twoDishName) {
        TwoDishName = twoDishName;
    }

    public String getTwoDishRecImage() {
        return TwoDishRecImage;
    }

    public void setTwoDishRecImage(String twoDishRecImage) {
        TwoDishRecImage = twoDishRecImage;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    private Integer level;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
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

    public Integer getLevel() {return level;}

    public void setLevel(Integer level) {this.level = level;}
}