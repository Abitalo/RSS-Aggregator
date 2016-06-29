package com.abitalo.www.rss_aggregator.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.abitalo.www.rss_aggregator.constants.Conf;
import com.abitalo.www.rss_aggregator.constants.MessageWhat;
import com.abitalo.www.rss_aggregator.entity.MeetUser;
import com.abitalo.www.rss_aggregator.entity.RssSource;
import com.abitalo.www.rss_aggregator.entity.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;
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
    private Handler handler;
    private ArrayList<String> userRegisterSet = new ArrayList<>();

    public UserRssEditHelper(Context context, String url, Handler handler){
        this.context = context;
        this.url = url;
        this.handler = handler;
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
                if(list.size() > 0){
                    userId = list.get(0).getObjectId();
                    getUserDataId(option);
                }else {
                    if (handler != null){
                        Message message = new Message();
                        message.what = MessageWhat.NOT_LOGIN;
                        Bundle bundle = new Bundle();
                        bundle.putString("Login", "Not login");
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::"+i);
                Log.i("RegisterHelper", "Message:::"+s);
            }
        });
    }

    private void getUserDataId(final int option){
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
    }

    private void deleteContain() {
        Log.i("UserRssEditHelper", userRegisterSet.contains(url)+"");
        if (userRegisterSet.contains(url)){
            userRegisterSet.remove(url);
        }
    }


    private void checkContain() {
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

    private void updateTendency(final Integer option){
        BmobQuery<RssSource> query=new BmobQuery<RssSource>("rss_source");
        query.addWhereEqualTo("rssUrl",url);
        query.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try{
                    final Integer facetId=jsonArray.getJSONObject(0).getInt("facetId");
                    BmobQuery<UserData> queryForUserData=new BmobQuery<>();
                    final String constUserDataId=userDataId;
                    queryForUserData.getObject(context, constUserDataId, new GetListener<UserData>() {
                        @Override
                        public void onSuccess(UserData userData) {
                            if(option==ADD)
                                userData.getTendencies().set(facetId,userData.getTendencies().get(facetId)+ Conf.SUBSCRIBE_RSS);
                            if(option==DELETE)
                                userData.getTendencies().set(facetId,userData.getTendencies().get(facetId)- Conf.SUBSCRIBE_RSS);
                            userData.update(context);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }catch (JSONException e){
                    Log.i("abitalo.UserRssEdit",e.getMessage());
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
}
