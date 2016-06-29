package com.abitalo.www.rss_aggregator.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.abitalo.www.rss_aggregator.constants.MessageWhat;
import com.abitalo.www.rss_aggregator.entity.RssSource;

import java.util.ArrayList;

/**
 * Created by sangz on 2016/6/10.
 */
public class MainPageHelper extends Thread {
    private boolean isRunning = true;
    private Handler handler = null;

    public MainPageHelper(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        ArrayList<RssSource> rssSources;
        Bundle bundle = new Bundle();
        bundle.putInt("listCount", 3);
        for (int j = 0; j < 3; j++) {
            rssSources = new ArrayList<>();
            RssSource rssSource;
            for (int i = 0; i < 2; i++) {
                rssSource = new RssSource();
                rssSource.setRssName("12");
                rssSource.setRssUrl("123123");
                rssSources.add(rssSource);
            }
            bundle.putParcelableArrayList("rss" + j, rssSources);
        }

        if (!isRunning) {
            return;
        }
        Message message = new Message();
        message.what = MessageWhat.LOAD_SUCCESS;
        message.setData(bundle);
        handler.sendMessage(message);


    }

    public void stopLoad() {
        isRunning = false;
    }
}
