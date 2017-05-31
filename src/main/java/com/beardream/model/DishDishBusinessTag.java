package com.beardream.model;

import java.util.Date;
import java.util.List;

/**
 * Created by soft01 on 2017/5/26.
 */
public class DishDishBusinessTag {
    private Integer dishBusinessId;

    private Integer dishId;

    private Integer tagId;

    private Integer businessId;

    private  String dishType;

    private String dishName;

    private String tagName;

    private String dishImage;

    private Date addTime;

    private List<DishDishBusinessTag>dishDishBusinessTags;

    public List<DishDishBusinessTag> getDishDishBusinessTags() {
        return dishDishBusinessTags;
    }

    public void setDishDishBusinessTags(List<DishDishBusinessTag> dishDishBusinessTags) {
        this.dishDishBusinessTags = dishDishBusinessTags;
    }

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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
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
        this.dishType = dishType;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }


}
