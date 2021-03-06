package com.beardream.model;

import java.util.Date;

public class Number {
    private Integer numId;

    private Integer userId;

    private Integer businessId;

    private Integer number;

    private Byte isExpired;

    private Integer peopleNum;

    private Date addTime;

    // 用户信息
    private String username;

    private String headImgUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Number() {
    }

    public Number(Integer businessId, Date addTime, Integer number) {
        this.businessId = businessId;
        this.addTime = addTime;
        this.number = number;
    }

    public Integer getNumId() {
        return numId;
    }

    public void setNumId(Integer numId) {
        this.numId = numId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Byte getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Byte isExpired) {
        this.isExpired = isExpired;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}