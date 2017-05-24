package com.beardream.model;

import java.util.Date;

/**
 * Created by bearDream on 2017/5/24.
 * 商家信息和商家排队取号的信息
 */
public class BusinessNumber {

    private Business business;

    // 当前号
    private Integer number;

    // 等待人数
    private Integer waitNumber;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getWaitNumber() {
        return waitNumber;
    }

    public void setWaitNumber(Integer waitNumber) {
        this.waitNumber = waitNumber;
    }
}
