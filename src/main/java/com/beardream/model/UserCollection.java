package com.beardream.model;

import java.util.Date;

public class UserCollection {
    private Integer collectionId;

    private Integer userId;

    private Integer businessDishId;

    private Integer collectionType;

    private Date addTime;

    // 菜品相关属性
    private Integer dishId;

    private String dishName;

    private String dishImage;

    private String dishRecImage;

    // 商家相关属性
    private Integer businessId;

    private String name;

    private String address;

    private String businessImage;

    private String content;

    // 文章相关属性
    private Integer articleId;

    private String articleUsername;

    private String articleUserAvatar;

    private String coverImage;

    private String ArticleContent;

    private String title;

    private Integer comment; // 评论数量

    private Date ArticleAddTime;

    public UserCollection() {
    }

    public UserCollection(Integer userId, Integer businessDishId) {
        this.userId = userId;
        this.businessDishId = businessDishId;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getArticleUserAvatar() {
        return articleUserAvatar;
    }

    public void setArticleUserAvatar(String articleUserAvatar) {
        this.articleUserAvatar = articleUserAvatar;
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

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getDishRecImage() {
        return dishRecImage;
    }

    public void setDishRecImage(String dishRecImage) {
        this.dishRecImage = dishRecImage;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(String businessImage) {
        this.businessImage = businessImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinessDishId() {
        return businessDishId;
    }

    public void setBusinessDishId(Integer businessDishId) {
        this.businessDishId = businessDishId;
    }

    public Integer getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Integer collectionType) {
        this.collectionType = collectionType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}