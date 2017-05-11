package com.beardream.model;

import java.util.Date;

public class Role {
    private Integer roleId;

    private String roleName;

    private String roleContent;

    private Date addTime;

    private Byte roleYxbz;

    private Byte roleType;

    private String promission;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleContent() {
        return roleContent;
    }

    public void setRoleContent(String roleContent) {
        this.roleContent = roleContent == null ? null : roleContent.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Byte getRoleYxbz() {
        return roleYxbz;
    }

    public void setRoleYxbz(Byte roleYxbz) {
        this.roleYxbz = roleYxbz;
    }

    public Byte getRoleType() {
        return roleType;
    }

    public void setRoleType(Byte roleType) {
        this.roleType = roleType;
    }

    public String getPromission() {
        return promission;
    }

    public void setPromission(String promission) {
        this.promission = promission == null ? null : promission.trim();
    }
}