package com.beardream.model;

import java.util.Date;

public class Log {
    private Integer logId;

    private Date logAddtime;

    private String actionkey;

    private String logContent;

    private String controllerkey;

    private Integer userId;

    private String logIp;

    private String params;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Date getLogAddtime() {
        return logAddtime;
    }

    public void setLogAddtime(Date logAddtime) {
        this.logAddtime = logAddtime;
    }

    public String getActionkey() {
        return actionkey;
    }

    public void setActionkey(String actionkey) {
        this.actionkey = actionkey == null ? null : actionkey.trim();
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }

    public String getControllerkey() {
        return controllerkey;
    }

    public void setControllerkey(String controllerkey) {
        this.controllerkey = controllerkey == null ? null : controllerkey.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp == null ? null : logIp.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }
}