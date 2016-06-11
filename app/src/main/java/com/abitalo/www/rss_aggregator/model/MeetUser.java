package com.abitalo.www.rss_aggregator.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by Lancelot on 2016/5/4.
 * 用户实体类
 */
public class MeetUser extends BmobUser {

    public MeetUser(){
        this.setTableName("_User");
    }


    //已包括
    // objectId,username,password,
    // mobilePhoneNumberVerified,mobilePhoneNumber,
    //emailVerified,email,
    //以及BmobObject的属性

    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}
