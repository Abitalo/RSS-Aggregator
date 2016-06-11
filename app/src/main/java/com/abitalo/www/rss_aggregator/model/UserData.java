package com.abitalo.www.rss_aggregator.model;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by sangz on 2016/6/11.
 */
public class UserData extends BmobObject {
    private String userId;
    private ArrayList<String> userRegisterSet;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getUserRegisterSet() {
        return userRegisterSet;
    }

    public void setUserRegisterSet(ArrayList<String> userRegisterSet) {
        this.userRegisterSet = userRegisterSet;
    }
}
