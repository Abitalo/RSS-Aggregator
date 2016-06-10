package com.abitalo.www.rss_aggregator.model;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by sangz on 2016/6/11.
 */
public class UserData extends BmobObject {
    public UserData() {
        this.setTableName("user_data");
    }

    private String userId;
    private ArrayList userRegisterSet;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList getUserRegisterSet() {
        return userRegisterSet;
    }

    public void setUserRegisterSet(ArrayList userRegisterSet) {
        this.userRegisterSet = userRegisterSet;
    }
}
