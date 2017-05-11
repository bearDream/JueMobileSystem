package com.beardream.model;

import java.util.Date;

public class Collection {
    private Integer collectionId;

    private Integer userId;

    private Integer businessDishId;

    private Byte collectionType;

    private Date addTime;

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinessDishId() {
        return businessDishId;
    }

    public void setBusinessDishId(Integer businessDishId) {
        this.businessDishId = businessDishId;
    }

    public Byte getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Byte collectionType) {
        this.collectionType = collectionType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}