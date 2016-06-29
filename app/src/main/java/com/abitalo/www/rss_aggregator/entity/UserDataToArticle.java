package com.abitalo.www.rss_aggregator.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Lancelot on 2016/6/22.
 */
public class UserDataToArticle extends BmobObject{
    private String userDataId;
    private String articleId;

    public UserDataToArticle(){
        setTableName("userdata_to_article");
    }

    public String getUserDataId() {
        return userDataId;
    }

    public void setUserDataId(String userId) {
        this.userDataId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
