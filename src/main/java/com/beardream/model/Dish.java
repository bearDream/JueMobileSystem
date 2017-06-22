package com.beardream.model;

import java.util.Date;

public class Dish {
    private Integer dishId;

    private String dishName;

    private String dishDesc;

    private Integer dishtypeId;

    private String dishImage;

    private Date addTime;

    private String dishContent;

    private String typeName;

    private String dishRecImage;

    private Integer nurtritionId;

    private String grease;

    private String heat;

    private String sugarContent;

    private Integer dishNutritionStatus;

    // 用户收藏菜品的colletionId
    private Integer collectionId;

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getDishtypeId() {
        return dishtypeId;
    }

    public void setDishtypeId(Integer dishtypeId) {
        this.dishtypeId = dishtypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDishRecImage() {
        return dishRecImage;
    }

    public void setDishRecImage(String dishRecImage) {
        this.dishRecImage = dishRecImage;
    }

    public Integer getNurtritionId() {
        return nurtritionId;
    }

    public void setNurtritionId(Integer nurtritionId) {
        this.nurtritionId = nurtritionId;
    }

    public String getSugarContent() {
        return sugarContent;
    }

    public void setSugarContent(String sugarContent) {
        this.sugarContent = sugarContent;
    }

    public String getGrease() {
        return grease;
    }

    public void setGrease(String grease) {
        this.grease = grease;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public Integer getDishNutritionStatus() {
        return dishNutritionStatus;
    }

    public void setDishNutritionStatus(Integer dishNutritionStatus) {
        this.dishNutritionStatus = dishNutritionStatus;
    }

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
        this.dishName = dishName == null ? null : dishName.trim();
    }

    public String getDishDesc() {
        return dishDesc;
    }

    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc == null ? null : dishDesc.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getDishContent() {
        return dishContent;
    }

    public void setDishContent(String dishContent) {
        this.dishContent = dishContent == null ? null : dishContent.trim();
    }


}