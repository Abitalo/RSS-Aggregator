package com.abitalo.www.rss_aggregator.helper;

import android.content.Context;
import android.util.Log;

import com.abitalo.www.rss_aggregator.entity.MeetUser;
import com.abitalo.www.rss_aggregator.entity.UserData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by sangz on 2016/6/11.
 */
public class RegisterHelper {
    private String userName;
    private Context context;
    private String userId;

    public RegisterHelper(Context context, String userName) {
        this.context = context;
        this.userName = userName;
    }

    public void getUser() {
        BmobQuery<MeetUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", userName);
        bmobQuery.addQueryKeys("objectId");

        bmobQuery.findObjects(context, new FindListener<MeetUser>(){

            @Override
            public void onSuccess(List<MeetUser> list) {
                userId = list.get(0).getObjectId();
                createUserData();
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::"+i);
                Log.i("RegisterHelper", "Message:::"+s);
            }
        });
    }

    private void createUserData() {
        UserData userData = new UserData();
        userData.setUserId(userId);
        Log.i("RegisterHelper", "userId:::"+userId);
        userData.setUserRegisterSet(new ArrayList());
        //13个0
        Double[] array={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        userData.setTendencies(new ArrayList<Double>(Arrays.asList(array)));
        userData.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("RegisterHelper", "Is here visited?");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("RegisterHelper", "code:::"+i);
                Log.i("RegisterHelper", "Message:::"+s);
            }
        });

    }
}
