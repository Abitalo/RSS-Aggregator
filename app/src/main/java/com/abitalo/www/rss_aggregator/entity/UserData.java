package com.abitalo.www.rss_aggregator.entity;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by sangz on 2016/6/11.
 */
public class UserData extends BmobObject {
    private String userId;
    private ArrayList<String> userRegisterSet;
    private ArrayList<Double> tendencies;
    private String iconUrl;

    public UserData(){
        setTableName("UserData");
    }

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

    public ArrayList<Double> getTendencies() {
        return tendencies;
    }

    public void setTendencies(ArrayList<Double> tendencies) {
        this.tendencies = tendencies;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
