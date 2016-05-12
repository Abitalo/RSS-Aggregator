package com.abitalo.www.rss_aggregator.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Zela on 2016/5/5.
 */
public class RSSItem implements Parcelable {
    private String title;
    private String pubdate;
    private ArrayList<String> categories;
    private String link;
    private String description;

    protected RSSItem(Parcel in) {
        title = in.readString();
        pubdate = in.readString();
        in.readStringList(categories);
        link = in.readString();
        description = in.readString();
    }

    public static final Creator<RSSItem> CREATOR = new Creator<RSSItem>() {
        @Override
        public RSSItem createFromParcel(Parcel in) {
            return new RSSItem(in);
        }

        @Override
        public RSSItem[] newArray(int size) {
            return new RSSItem[size];
        }
    };

    public RSSItem() {
        categories = new ArrayList<String>();
    }

    public String getTitle() {
        if (title.length() > 20) {
            return title.substring(0, 19) + "...";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category) {
        if(null == categories){
            categories = new ArrayList<String>();
        }
        categories.add(category);
    }

    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pubdate);
        dest.writeStringList(categories);
        dest.writeString(link);
        dest.writeString(description);
    }
}
