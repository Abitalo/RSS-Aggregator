package com.abitalo.www.rss_aggregator.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.abitalo.www.rss_aggregator.model.MeetUser;
import com.abitalo.www.rss_aggregator.model.RssSource;
import com.abitalo.www.rss_aggregator.model.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
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
    public static int ADD = 0;
    public static int DELETE = 1;
    private ArrayList<String> userRegisterSet = new ArrayList<>();

    public UserRssEditHelper(Context context, String url){
        this.context = context;
        this.url = url;

        SharedPreferences sharedPreferences= context.getSharedPreferences("userAuthentication",
                Activity.MODE_PRIVATE);
        userName =sharedPreferences.getString("name", "");
    }

    public void getUserId(final int option){
        BmobQuery<MeetUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", userName);
        bmobQuery.addQueryKeys("objectId");

        bmobQuery.findObjects(context, new FindListener<MeetUser>(){

            @Override
            public void onSuccess(List<MeetUser> list) {
                userId = list.get(0).getObjectId();
                getUserDataId(option);
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::"+i);
                Log.i("RegisterHelper", "Message:::"+s);
            }
        });
    }

    private void getUserDataId(final int option){
//        BmobQuery<UserData> bmobQuery = new BmobQuery<>();
        BmobQuery bmobQuery = new BmobQuery("UserData");
        bmobQuery.addWhereEqualTo("userId", userId);
        bmobQuery.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.i("UserRssSourceHelper", userId);
                JSONObject jsonObject;
                ArrayList<RssSource> rssSources = new ArrayList<>();
                rssSources.add(new RssSource());
                assert jsonArray != null;
                try {
                    jsonObject = jsonArray.getJSONObject(0);
                    userDataId = jsonObject.get("objectId").toString();
                    Log.i("UserRssSourceHelper", jsonObject.get("userRegisterSet").toString());
                    if (jsonObject.get("userRegisterSet").toString().length() != 0){
                        JSONArray userRegisterSetJSONArray = (JSONArray) jsonObject.get("userRegisterSet");
                        for (int j = 0; j < userRegisterSetJSONArray.length(); j++) {
                            userRegisterSet.add(userRegisterSetJSONArray.get(j).toString());
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (option == ADD ){
                    checkContain();
                }else if (option == DELETE){
                    deleteContain();
                }
                updateUserData();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("RegisterHelper2", "code:::" + i);
                Log.i("RegisterHelper2", "Message:::" + s);
            }
        });
        /*bmobQuery.findObjects(context, new FindListener<UserData>(){
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
        });*/
    }

    private void deleteContain() {
        Log.i("UserRssEditHelper", userRegisterSet.contains(url)+"");
        if (userRegisterSet.contains(url)){
            userRegisterSet.remove(url);
        }
    }


    private void checkContain() {
       /* boolean isFind = false;
        for (int i = 0; i < userRegisterSet.size(); i++){
            if (userRegisterSet.get(i).equals(url)){
                isFind = true;
            }
        }
        if (!isFind){
            userRegisterSet.add(url);
        }*/
        if (!userRegisterSet.contains(url)){
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
