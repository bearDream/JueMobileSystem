package com.beardream.model;

import java.util.Date;
import java.util.List;

/**
 * Created by soft01 on 2017/5/23.
 */
public class UserArticle {
    private Integer userId;

    private String username;

    private String tel;

    private String headImgUrl;

    private Integer articleId;

    private String content;

    private String coverImage;

    private String recImage;

    // 用于前端显示 recImage的小图片集合
    private List<String> recImageList;

    private Integer praise;

    private Byte isShow;

    private Date addTime;

    private String title;

    // 收藏表的信息
    private Integer collectionId;

    private Integer collectionType;

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Integer collectionType) {
        this.collectionType = collectionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public List<String> getRecImageList() {
        return recImageList;
    }

    public void setRecImageList(List<String> recImageList) {
        this.recImageList = recImageList;
    }

    public String getRecImage() {
        return recImage;
    }

    public void setRecImage(String recImage) {
        this.recImage = recImage;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {return userId;}

    public void setUserId(Integer userId) {this.userId = userId;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getTel() {return tel;}

    public void setTel(String tel) {this.tel = tel;}

    public Integer getArticleId() {return articleId;}

    public void setArticleId(Integer articleId) {this.articleId = articleId;}

    public String getCoverImage() {return coverImage;}

    public void setCoverImage(String coverImage) {this.coverImage = coverImage;}

    public Byte getIsShow() {return isShow;}

    public void setIsShow(Byte isShow) {this.isShow = isShow;}

    public Date getAddTime() {return addTime;}

    public void setAddTime(Date addTime) {this.addTime = addTime;}
}
