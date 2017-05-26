package com.beardream.model;

/**
 * Created by soft01 on 2017/5/25.
 */
public class DishTypeBusinessTag {
    private Integer dishtypeId;

    private Integer businessId;

    private Integer tagId;

    private Integer dishId;

    private String typeName;

    private String businessName;

    private String businessTel;

    private String tagName;

    public Integer getDishtypeId() {
        return dishtypeId;
    }

    public void setDishtypeId(Integer dishtypeId) {
        this.dishtypeId = dishtypeId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessTel() {
        return businessTel;
    }

    public void setBusinessTel(String businessTel) {
        this.businessTel = businessTel;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
