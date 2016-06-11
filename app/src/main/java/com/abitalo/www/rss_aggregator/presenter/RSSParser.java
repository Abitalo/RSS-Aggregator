package com.abitalo.www.rss_aggregator.presenter;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d

import com.abitalo.www.rss_aggregator.constants.Messages;
import com.abitalo.www.rss_aggregator.util.RSSHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

/**
 * Created by Zela on 2016/5/5.
 */
public class RSSParser extends Thread{
<<<<<<< HEAD
    String urlStr =null;
    Handler handler = null;
=======
    private boolean isRuning = true;
    private String urlStr =null;
    private Handler handler = null;
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d

    public RSSParser(String urlStr, Handler handler){
        this.urlStr=urlStr;
        this.handler=handler;
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(urlStr);
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            RSSHandler rssHandler = new RSSHandler();
            xmlReader.setContentHandler(rssHandler);

            CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
            detector.add(JChardetFacade.getInstance());
            Charset charset = detector.detectCodepage(url);
            String encodingName = charset.name();

            InputSource inputSource = null;
            InputStream stream = null;
            // 如果是GB2312编码
            if ("GBK".equalsIgnoreCase(encodingName)||"gb2312".equalsIgnoreCase(encodingName)) {
                stream = url.openStream();
                // 通过InputStreamReader设定编码方式
                InputStreamReader streamReader = new InputStreamReader(stream,
                        encodingName);
                inputSource = new InputSource(streamReader);
                xmlReader.parse(inputSource);
            } else {
                // 是utf-8编码
                inputSource = new InputSource(url.openStream());
                inputSource.setEncoding("UTF-8");
                xmlReader.parse(inputSource);
            }
            Bundle  bundle =new Bundle();
<<<<<<< HEAD
            bundle.putParcelableArrayList("list",rssHandler.getRSSFeed().getList());
=======
            bundle.putString("title",rssHandler.getRSSFeed().getTitle());
            bundle.putParcelableArrayList("list",rssHandler.getRSSFeed().getList());
            if(false == isRuning){
                return;
            }
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
            Message msg = new Message();
            msg.what= Messages.RSS_PARSE_SUCCESS;
            msg.setData(bundle);
            handler.sendMessage(msg);
<<<<<<< HEAD
            Log.e("LOGCAT", "5555555555555555555555555555555555555555555555555555555556sad5asdasdsada");
=======
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what= Messages.URL_ILLEAGEL;
            handler.sendMessage(msg);
        }
    }
<<<<<<< HEAD
=======

    public void stopPulling(){
        isRuning=false;
    }
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
}
