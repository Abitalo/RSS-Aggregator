package com.abitalo.www.rss_aggregator.model;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by sangzhenya on 2016/5/9.
 * 分类实体类
 */
public class Facet extends BmobObject implements Parcelable{
    private int id;
    private String facetName;
    private String backgroundUrl;

    public Facet() {
        this.setTableName("facet");
    }

    protected Facet(Parcel in) {
        id = in.readInt();
        facetName = in.readString();
        backgroundUrl = in.readString();
    }

    public static final Creator<Facet> CREATOR = new Creator<Facet>() {
        @Override
        public Facet createFromParcel(Parcel in) {
            return new Facet(in);
        }

        @Override
        public Facet[] newArray(int size) {
            return new Facet[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(facetName);
        dest.writeString(backgroundUrl);
    }
}
