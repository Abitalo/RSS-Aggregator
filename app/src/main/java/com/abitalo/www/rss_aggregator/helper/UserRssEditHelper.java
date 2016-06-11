package com.abitalo.www.rss_aggregator.helper;

import android.content.Context;
import android.util.Log;

import com.abitalo.www.rss_aggregator.model.MeetUser;
import com.abitalo.www.rss_aggregator.model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by sangz on 2016/6/11.
 */
public class UserRssEditHelper {
    private Context context;
    private String userName;
    private String url;
    private String userId;
    private String userDataId;
    private ArrayList<String> userRegisterSet;

    public UserRssEditHelper(Context context, String userName, String url){
        this.context = context;
        this.userName = userName;
        this.url = url;
    }

    public void getUserId(){
        BmobQuery<MeetUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", userName);
        bmobQuery.addQueryKeys("objectId");

        bmobQuery.findObjects(context, new FindListener<MeetUser>(){

            @Override
            public void onSuccess(List<MeetUser> list) {
                userId = list.get(0).getObjectId();
                getUserDataId();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::"+i);
                Log.i("RegisterHelper", "Message:::"+s);
            }
        });
    }

    private void getUserDataId(){
        BmobQuery<UserData> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userId", userId);
        bmobQuery.findObjects(context, new FindListener<UserData>(){
            @Override
            public void onSuccess(List<UserData> list) {
                userDataId = list.get(0).getObjectId();
                userRegisterSet = list.get(0).getUserRegisterSet();
                checkContain();
                updateUserData();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::"+i);
                Log.i("RegisterHelper", "Message:::"+s);
            }
        });
    }

    private void checkContain() {
        boolean isFind = false;
        for (int i = 0; i < userRegisterSet.size(); i++){
            if (userRegisterSet.get(0).equals(url)){
                isFind = true;
            }
        }
        if (!isFind){
            userRegisterSet.add(url);
        }
    }

    private void updateUserData() {
        UserData userData = new UserData();
        userData.setUserRegisterSet(userRegisterSet);
        userData.update(context, userDataId, new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("RegisterHelper", "code:::"+i);
                Log.i("RegisterHelper", "Message:::"+s);
            }
        });


    }
}
