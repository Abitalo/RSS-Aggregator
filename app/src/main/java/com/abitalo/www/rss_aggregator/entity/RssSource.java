package com.abitalo.www.rss_aggregator.entity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by sangz on 2016/5/12.
 * 源实体类
 */
public class RssSource extends BmobObject implements Parcelable {
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

    /**************************************************************/

    protected RssSource(Parcel in) {
        id = in.readInt();
        rssName = in.readString();
        rssUrl = in.readString();
        rssIcon = in.readString();
        facetId = in.readInt();
    }

    public static final Creator<RssSource> CREATOR = new Creator<RssSource>() {
        @Override
        public RssSource createFromParcel(Parcel in) {
            return new RssSource(in);
        }

        @Override
        public RssSource[] newArray(int size) {
            return new RssSource[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(rssName);
        dest.writeString(rssUrl);
        dest.writeString(rssIcon);
        dest.writeInt(facetId);
    }
}
