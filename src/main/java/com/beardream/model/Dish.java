package com.beardream.model;

import java.util.Date;

public class Dish {
    private Integer dishId;

    private String dishName;

    private String dishImage;

    private String dishDesc;

    private Date addTime;

    private String dishContent;

    private Integer dishNutritionStatus;

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