package com.abitalo.www.rss_aggregator.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by sangz on 2016/5/12.
 * 源实体类
 */
public class RssSource extends BmobObject {
    private int id;
    private String rssName;
    private String rssUrl;
    private String rssIcon;

    private int facetId;

    public RssSource(){
        this.setTableName("rss_source");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRssName() {
        return rssName;
    }

    public void setRssName(String rssName) {
        this.rssName = rssName;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public String getRssIcon() {
        return rssIcon;
    }

    public void setRssIcon(String rssIcon) {
        this.rssIcon = rssIcon;
    }

    public int getFacetId() {
        return facetId;
    }

    public void setFacetId(int facetId) {
        this.facetId = facetId;
    }
}
