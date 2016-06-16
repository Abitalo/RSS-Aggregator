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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by sangz on 2016/6/12.
 */
public class UserAccountViewHelper extends Thread{
    private boolean isRunning = true;
    private Handler handler = null;
    private Context context;
    private ArrayList<String> userInfo = new ArrayList<>();

    public UserAccountViewHelper(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userAuthentication",
                Activity.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        userInfo.add(name);
        BmobQuery<MeetUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", name);
        bmobQuery.addQueryKeys("email");

        bmobQuery.findObjects(context, new FindListener<MeetUser>() {

            @Override
            public void onSuccess(List<MeetUser> list) {
                try{
                    userInfo.add(list.get(0).getEmail());
                }catch (Exception exception){
                    exception.printStackTrace();
                    userInfo.add("");
                }
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("userInfo", userInfo);
                if (!isRunning) {
                    return;
                }
                Message message = new Message();
                message.what = MessageWhat.USER_INFO_LOAD_SUCCESS;
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::" + i);
                Log.i("RegisterHelper", "Message:::" + s);
            }
        });
    }


    public void stopLoad() {
        isRunning = false;
    }
}
