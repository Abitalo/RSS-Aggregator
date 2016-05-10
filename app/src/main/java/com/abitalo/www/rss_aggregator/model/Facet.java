package com.abitalo.www.rss_aggregator.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by sangzhenya on 2016/5/9.
 * 实体类
 */
public class Facet extends BmobObject {
    private int id;
    private String facetName;
    private String backgroundUrl;

    public Facet() {
        this.setTableName("facet");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacetName() {
        return facetName;
    }

    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }
}
