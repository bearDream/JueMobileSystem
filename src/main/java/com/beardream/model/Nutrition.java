package com.beardream.model;

import java.util.Date;

public class Nutrition {
    private Integer nurtritionId;

    private String grease;

    private String heat;

    private String sugarContent;

    private Date addTime;

    public Integer getNurtritionId() {
        return nurtritionId;
    }

    public void setNurtritionId(Integer nurtritionId) {
        this.nurtritionId = nurtritionId;
    }

    public String getGrease() {
        return grease;
    }

    public void setGrease(String grease) {
        this.grease = grease == null ? null : grease.trim();
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat == null ? null : heat.trim();
    }

    public String getSugarContent() {
        return sugarContent;
    }

    public void setSugarContent(String sugarContent) {
        this.sugarContent = sugarContent == null ? null : sugarContent.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}