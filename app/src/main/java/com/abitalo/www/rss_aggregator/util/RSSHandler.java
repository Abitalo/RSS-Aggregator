package com.abitalo.www.rss_aggregator.util;

import com.abitalo.www.rss_aggregator.model.RSSFeed;
import com.abitalo.www.rss_aggregator.model.RSSItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Zela on 2016/5/5.
 */

public class RSSHandler extends DefaultHandler {
    StringBuffer stringBuffer = null;
    RSSFeed rssFeed;
    RSSItem rssItem;

    boolean isInItem;

    final int RSS_TITLE = 1;
    final int RSS_LINK = 2;
    final int RSS_CATEGORY = 3;
    final int RSS_PUBDATE = 4;
    final int RSS_DESCRIPTION = 5;
    final int RSS_ENCODED = 6;
    final int RSS_AUTHOR = 7;
    int currentFlag = 0;

    public RSSHandler() {
        stringBuffer = new StringBuffer();
    }

    public RSSFeed getRSSFeed() {
        return rssFeed;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        isInItem = false;
        rssFeed = new RSSFeed();
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        // 获取字符串
//        Log.i("i", "要获取的内容： 测试回车" + ch);
        if (0 != currentFlag && stringBuffer!=null) {
            for (int i=start; i<start+length; i++) {
                stringBuffer.append(ch[i]);
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
//        Log.e("LOGCAT", "tag:" + localName);
        if ("channel".equals(localName)) {
            currentFlag = 0;
            return;
        }
        if ("item".equals(localName)) {
            isInItem = true;
            rssItem = new RSSItem();
            return;
        }
        if ("title".equals(localName)) {
            currentFlag = RSS_TITLE;
            return;
        }
        if ("description".equals(localName)) {
            currentFlag = RSS_DESCRIPTION;
            return;
        }
        if ("link".equals(localName)) {
            currentFlag = RSS_LINK;
            return;
        }
        if ("pubDate".equals(localName)) {
            currentFlag = RSS_PUBDATE;
            return;
        }
        if ("category".equals(localName)) {
            currentFlag = RSS_CATEGORY;
            return;
        }
        if("encoded".equals(localName)){
            currentFlag = RSS_ENCODED;
            return;
        }
        if("author".equals(localName)){
            currentFlag = RSS_AUTHOR;
            return;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        String string = stringBuffer.toString();
//        Log.e("LOGCAT", "内容：" +string);
        stringBuffer.delete(0,stringBuffer.length());
        if ("item".equals(localName)) {
            isInItem = false;
            rssFeed.addItem(rssItem);
            return;
        }
        else if ("title".equals(localName)) {
            if(isInItem)
                rssItem.setTitle(string);
            else
                rssFeed.setTitle(string);
            currentFlag = 0;
            return;
        }
        else if ("description".equals(localName)) {
            if(isInItem) {
                rssItem.setDescription(string);
                if(null==rssItem.getImageUrl()){
                    rssItem.setImageUrl(RSSContentFilter.getImgSrc(string));
                }
                if(null == rssItem.getMabstract()){
                    rssItem.setMabstract(RSSContentFilter.getAbstract(string));
                }
            }
            currentFlag = 0;
            return;
        }
        else if ("link".equals(localName)) {
            if(isInItem)
                rssItem.setLink(string);
            else
                rssFeed.setLink(string);
            currentFlag = 0;
            return;
        }
        else if ("pubDate".equals(localName)) {
            if(isInItem)
                rssItem.setPubdate(string);
            else
                rssFeed.setPubdate(string);
            currentFlag = 0;
            return;
        }
        else if ("category".equals(localName)) {
            if(isInItem)
                rssItem.addCategory(string);
            currentFlag = 0;
            return;
        }
        else if("encoded".equals(localName)){
            if(isInItem) {
                rssItem.setEncoded(string);
                if(null==rssItem.getImageUrl()){
                    rssItem.setImageUrl(RSSContentFilter.getImgSrc(string));
                }
                if(null == rssItem.getMabstract()){
                    rssItem.setMabstract(RSSContentFilter.getAbstract(string));
                }
            }
            currentFlag= 0 ;
            return;
        }
        else if("author".equals(localName)){
            if(isInItem){
                rssItem.setAuthor(string);
            }
            currentFlag = 0;
            return;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}