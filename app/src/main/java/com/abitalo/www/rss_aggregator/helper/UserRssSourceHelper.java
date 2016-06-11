package com.abitalo.www.rss_aggregator.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.abitalo.www.rss_aggregator.constants.MessageWhat;
import com.abitalo.www.rss_aggregator.model.Facet;
import com.abitalo.www.rss_aggregator.model.MeetUser;
import com.abitalo.www.rss_aggregator.model.RssSource;
import com.abitalo.www.rss_aggregator.model.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by sangz on 2016/6/11.
 */
public class UserRssSourceHelper extends Thread {
    private boolean isRunning = true;
    private Handler handler = null;
    private Context context;
    private String userId;
    private ArrayList<String> userRegisterSet = new ArrayList<>();
    private ArrayList<RssSource> rssSources = new ArrayList<>();

    public UserRssSourceHelper(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userAuthentication",
                Activity.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        BmobQuery<MeetUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", name);
        bmobQuery.addQueryKeys("objectId");

        bmobQuery.findObjects(context, new FindListener<MeetUser>() {

            @Override
            public void onSuccess(List<MeetUser> list) {
                userId = list.get(0).getObjectId();
                getUserData();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::" + i);
                Log.i("RegisterHelper", "Message:::" + s);
            }
        });


        BmobQuery query = new BmobQuery("facet");
        query.order("id");
        query.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray arg0) {
                JSONArray jsonArray = null;
                JSONObject jsonObject;
                try {
                    jsonArray = new JSONArray(arg0.toString());
                } catch (JSONException e) {
//                    showToast(e.toString());
                }

                ArrayList<Facet> facets = new ArrayList<>();
                facets.add(new Facet());
                assert jsonArray != null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                        Facet facet = new Facet();
                        facet.setId(Integer.parseInt(jsonObject.get("id").toString()));
                        facet.setFacetName(jsonObject.get("facetName").toString());
                        facet.setBackgroundUrl(jsonObject.get("backgroundUrl").toString());
                        facets.add(facet);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("facets", facets);
                if (!isRunning) {
                    return;
                }
                Message message = new Message();
                message.what = MessageWhat.FACET_LODE_SUCCESS;
                message.setData(bundle);
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Log.i("RegisterHelper", "code:::" + arg0);
                Log.i("RegisterHelper", "Message:::" + arg1);
            }
        });
    }

    private void getUserData() {
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
                getRssSourceData();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("RegisterHelper", "code:::" + i);
                Log.i("RegisterHelper", "Message:::" + s);
            }
        });
        /*bmobQuery.findObjects(context, new FindListener<UserData>() {
            @Override
            public void onSuccess(List<UserData> list) {
                userRegisterSet = list.get(0).getUserRegisterSet();
                getRssSourceData();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::" + i);
                Log.i("RegisterHelper", "Message:::" + s);
            }
        });*/
    }

    private void getRssSourceData() {
        for (int i = 0; i < userRegisterSet.size(); i++) {
            BmobQuery query = new BmobQuery("rss_source");
            query.addWhereEqualTo("rssUrl", userRegisterSet.get(i));
            query.findObjects(context, new FindCallback() {
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    JSONObject jsonObject;
                    assert jsonArray != null;
                    try {
                        jsonObject = jsonArray.getJSONObject(0);
                        RssSource rssSource = new RssSource();
                        rssSource.setId(Integer.parseInt(jsonObject.get("id").toString()));
                        rssSource.setRssName(jsonObject.get("rssName").toString());
                        rssSource.setRssUrl(jsonObject.get("rssUrl").toString());
                        rssSource.setRssIcon(jsonObject.get("rssIcon").toString());
                        rssSources.add(rssSource);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int arg0, String arg1) {
//                showToast("查询失败:" + arg1);
                }
            });
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("rssSources", rssSources);
        if (!isRunning) {
            return;
        }
        Message message = new Message();
        message.what = MessageWhat.RSS_SOURCE_LOAD_SUCCESS;
        message.setData(bundle);
        handler.sendMessage(message);

    }

    public void stopLoad() {
        isRunning = false;
    }
}
