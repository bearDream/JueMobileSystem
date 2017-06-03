package com.beardream.model;

import java.util.Date;

public class Evaluate {
    private Integer evaluateId;

    private Integer userId;

    private String username;

    private String headImgUrl;

    private boolean bodyStatus;

    private Integer objectId;

    private Byte evaluateType;

    private Byte star;

    private Byte isAnonymous;

    private Date addTime;

    private String content;

    // 文章相关属性
    private Integer articleId;

    private String articleUsername;

    private String articleUserAvatar;

    private String coverImage;

    private String ArticleContent;

    private String title;

    private Date ArticleAddTime;

    public boolean isBodyStatus() {
        return bodyStatus;
    }

    public void setBodyStatus(boolean bodyStatus) {
        this.bodyStatus = bodyStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleUsername() {
        return articleUsername;
    }

    public void setArticleUsername(String articleUsername) {
        this.articleUsername = articleUsername;
    }

    public String getArticleUserAvatar() {
        return articleUserAvatar;
    }

    public void setArticleUserAvatar(String articleUserAvatar) {
        this.articleUserAvatar = articleUserAvatar;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getArticleAddTime() {
        return ArticleAddTime;
    }

    public void setArticleAddTime(Date articleAddTime) {
        ArticleAddTime = articleAddTime;
    }

    public Integer getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Integer evaluateId) {
        this.evaluateId = evaluateId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Byte getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(Byte evaluateType) {
        this.evaluateType = evaluateType;
    }

    public Byte getStar() {
        return star;
    }

    public void setStar(Byte star) {
        this.star = star;
    }

    public Byte getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Byte isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}