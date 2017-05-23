package com.beardream.model;

import java.util.Date;

/**
 * Created by soft01 on 2017/5/23.
 */
public class UserArticle {
    private Integer userId;

    private String username;

    private String tel;

    private Integer articleId;

    private String content;

    private String coverImage;

    private Byte isShow;

    private Date addTime;

    public Integer getUserId() {return userId;}

    public void setUserId(Integer userId) {this.userId = userId;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getTel() {return tel;}

    public void setTel(String tel) {this.tel = tel;}

    public Integer getArticleId() {return articleId;}

    public void setArticleId(Integer articleId) {this.articleId = articleId;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public String getCoverImage() {return coverImage;}

    public void setCoverImage(String coverImage) {this.coverImage = coverImage;}

    public Byte getIsShow() {return isShow;}

    public void setIsShow(Byte isShow) {this.isShow = isShow;}

    public Date getAddTime() {return addTime;}

    public void setAddTime(Date addTime) {this.addTime = addTime;}
}
