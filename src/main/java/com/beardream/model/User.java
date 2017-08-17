package com.beardream.model;

public class User {
    private Integer userId;

    private String username;

    private String password;

    private String tel;

    private String headImgUrl;

    private String remark;

    private String realName;

    private Integer age;

    private String sex;

    private String idCard;

    private String bankCard;

    private String address;

    private String weixin;

    private String openid;

    private Integer logins;

    private String alipay;

    private Byte status;

    private Byte type;

    private Byte level;

    private Double lontitude;

    private Double latitude;

    private Integer roleId;

    private Integer point;

    private Byte bodyStatus;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLontitude() {
        return lontitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLontitude(Double lontitude) {
        this.lontitude = lontitude;
    }

    public User() {
    }
    public User(String openid) {
        this.openid = openid;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Byte getBodyStatus() {
        return bodyStatus;
    }

    public void setBodyStatus(Byte bodyStatus) {
        this.bodyStatus = bodyStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getLogins() {
        return logins;
    }

    public void setLogins(Integer logins) {
        this.logins = logins;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay == null ? null : alipay.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", remark='" + remark + '\'' +
                ", realName='" + realName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", idCard='" + idCard + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", address='" + address + '\'' +
                ", weixin='" + weixin + '\'' +
                ", openid='" + openid + '\'' +
                ", logins=" + logins +
                ", alipay='" + alipay + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", level=" + level +
                ", lontitude=" + lontitude +
                ", latitude=" + latitude +
                ", roleId=" + roleId +
                ", point=" + point +
                ", bodyStatus=" + bodyStatus +
                '}';
    }
}