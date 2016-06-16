package com.abitalo.www.rss_aggregator.helper;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.abitalo.www.rss_aggregator.constants.MessageWhat;
import com.abitalo.www.rss_aggregator.model.Facet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

/**
 * Created by sangz on 2016/6/11.
 */
public class FacetMainViewHelper extends Thread {
    private boolean isRunning = true;
    private Handler handler = null;
    private Context context;

    public FacetMainViewHelper(Context context, Handler handler){
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {
        BmobQuery query = new BmobQuery("facet");
        query.order("id");
        try {
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
                    if (!isRunning){
                        return;
                    }
                    Message message = new Message();
                    message.what = MessageWhat.FACET_LODE_SUCCESS;
                    message.setData(bundle);
                    handler.sendMessage(message);

                }
                @Override
                public void onFailure(int arg0, String arg1) {
//                showToast("查询失败:" + arg1);
                }
            });
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public void stopLoad(){
        isRunning = false;
    }
}
