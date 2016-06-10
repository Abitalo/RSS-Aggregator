package com.abitalo.www.rss_aggregator.helper;

import android.os.Handler;

import cn.bmob.v3.BmobQuery;

/**
 * Created by sangz on 2016/6/11.
 */
public class UserRssViewHelper extends Thread {
    private boolean isRunning = true;
    private String userName;
    private Handler handler = null;

    public UserRssViewHelper(String userName, Handler handler){
        this.userName = userName;
        this.handler = handler;
    }

    @Override
    public void run() {
        BmobQuery query = new BmobQuery("rss_source");
    }

    public void stopLoad(){
        isRunning = false;
    }
}
