package com.abitalo.www.rss_aggregator.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Zela on 2016/5/5.
 */
public class RSSItem implements Parcelable{
    private String title = null;
    private String pubdate = null;
    private ArrayList<String> categories = null;
    private String link = null;
    private String description = null;
    private String encoded = null;
    private String author = null;
<<<<<<< HEAD
=======
    private String mabstract = null;
    private String imageUrl = null;
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d

    protected RSSItem(Parcel in) {
        title = in.readString();
        pubdate = in.readString();
<<<<<<< HEAD
        in.readStringList(categories);
        link = in.readString();
        description = in.readString();
=======
        categories = in.createStringArrayList();
        link = in.readString();
        description = in.readString();
        encoded = in.readString();
        author = in.readString();
        mabstract = in.readString();
        imageUrl = in.readString();
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
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

<<<<<<< HEAD
=======
    public String getMabstract() {
        return mabstract;
    }

    public void setMabstract(String mabstract) {
        this.mabstract = mabstract;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
    public RSSItem() {
        categories = new ArrayList<String>();
    }

    public String getTitle() {
<<<<<<< HEAD
//        if (title.length() > 20) {
//            return title.substring(0, 19) + "...";
//        }
=======
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
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

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    public void addCategory(String category) {
        if(null == categories){
            categories = new ArrayList<String>();
        }
        categories.add(category);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
<<<<<<< HEAD
=======

>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
        dest.writeString(title);
        dest.writeString(pubdate);
        dest.writeStringList(categories);
        dest.writeString(link);
        dest.writeString(description);
<<<<<<< HEAD
    }
=======
        dest.writeString(encoded);
        dest.writeString(author);
        dest.writeString(mabstract);
        dest.writeString(imageUrl);
    }

>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
}
