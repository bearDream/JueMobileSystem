package com.beardream.enums;

/**
 * Created by soft01 on 2017/4/18.
 */
public enum ResultEnum {
    SUCCESS(0,"成功"),
    ERROR(-1,"未知错误"),
    Black_USERNAME(100,"你已被列入黑名单，不允许创建账户"),
    Logout(-2,"未登录"),
    NOPERMISSION(-2,"权限不足");
    ;

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
