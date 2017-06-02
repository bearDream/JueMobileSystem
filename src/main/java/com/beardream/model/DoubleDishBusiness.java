package com.beardream.model;

import java.util.Date;
import java.util.List;

/**
 * Created by soft01 on 2017/5/27.
 */
public class DoubleDishBusiness {

    private Integer businessId;

    private Integer userId;

    private String name;

    private String address;

    private String tel;

    private String businessImage;

    private Integer level;

    private List<Dish> dishList;

    private Integer dishId;

    private String dishName;

    private String dishImage;

    private String dishDesc;

    private Date addTime;

    private String dishType;

    private String tagId;

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public Integer getBusinessId() {return businessId;}

    public void setBusinessId(Integer businessId) {this.businessId = businessId;}

    public Integer getUserId() {return userId;}

    public void setUserId(Integer userId) {this.userId = userId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getTel() {return tel;}

    public void setTel(String tel) {this.tel = tel;}

    public String getBusinessImage() {return businessImage;}

    public void setBusinessImage(String businessImage) {this.businessImage = businessImage;}

    public Integer getLevel() {return level;}

    public void setLevel(Integer level) {this.level = level;}

    public Integer getDishId() {return dishId;}

    public void setDishId(Integer dishId) {this.dishId = dishId;}

    public String getDishName() {return dishName;}

    public void setDishName(String dishName) {this.dishName = dishName;}

    public String getDishImage() {return dishImage;}

    public void setDishImage(String dishImage) {this.dishImage = dishImage;}

    public String getDishDesc() {return dishDesc;}

    public void setDishDesc(String dishDesc) {this.dishDesc = dishDesc;}

    public Date getAddTime() {return addTime;}

    public void setAddTime(Date addTime) {this.addTime = addTime;}

    public String getDishType() {return dishType;}

    public void setDishType(String dishType) {this.dishType = dishType;}

    public String getTagId() {return tagId;}

    public void setTagId(String tagId) {this.tagId = tagId;}
}
