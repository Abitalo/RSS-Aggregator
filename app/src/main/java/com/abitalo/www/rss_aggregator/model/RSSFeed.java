package com.abitalo.www.rss_aggregator.model;

import java.util.ArrayList;

/**
 * Created by Zela on 2016/5/5.
 */
public class RSSFeed {
    private String title = null;
    private String pubdate = null;
    private String link = null;

    private int itemCount;
    private ArrayList<RSSItem> list;

    public int addItem(RSSItem rssItem){
        list.add(rssItem);
        itemCount++;
        return itemCount;
    }

    public RSSFeed() {
        list = new ArrayList<RSSItem>();
        itemCount = 0;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getPubdate() {
        return pubdate;
    }

    public int getItemCount() {
        return itemCount;
    }

    public ArrayList<RSSItem> getList() {
        return list;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setList(ArrayList<RSSItem> list) {
        this.list = list;
    }
}
