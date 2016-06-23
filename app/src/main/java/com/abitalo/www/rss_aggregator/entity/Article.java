package com.abitalo.www.rss_aggregator.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Lancelot on 2016/6/22.
 */
public class Article extends BmobObject{
    private String title;
    private String writer;
    private String content;
    private Integer facetIdNumber;
    private Integer favoritedTimes;

    public Article(){
        setTableName("article");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFacetIdNumber() {
        return facetIdNumber;
    }

    public void setFacetIdNumber(Integer facetIdNumber) {
        this.facetIdNumber = facetIdNumber;
    }

    public Integer getFavoritedTimes() {
        return favoritedTimes;
    }

    public void setFavoritedTimes(Integer favoritedTimes) {
        this.favoritedTimes = favoritedTimes;
    }
}
