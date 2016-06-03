package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.adapter.FacetAdapter;
import com.abitalo.www.rss_aggregator.adapter.RssSourceAdapter;
import com.abitalo.www.rss_aggregator.model.Facet;
import com.abitalo.www.rss_aggregator.model.RssSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

/**
 * Created by sangz on 2016/5/12.
 */
public class RssSourceView extends Fragment {
    private View view;
    private int facetId;

    android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    initView(bundle.getString("msg"));
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public RssSourceView() {
        this.facetId = 1;
    }

    public RssSourceView(int facetId) {
        this.facetId = facetId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.rss_source_main, container, false);
        getJSONRSSource();
        return view;
    }

    public void getJSONRSSource() {
        BmobQuery query = new BmobQuery("rss_source");
        query.addWhereEqualTo("facetId", 1);
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray arg0) {
                Log.i("RssSource", arg0.toString());
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", arg0.toString());
                message.setData(bundle);
                message.what = 1;
                Log.i("Rss", arg0.toString());
                handler.sendMessage(message);
            }


            @Override
            public void onFailure(int arg0, String arg1) {
                showToast("查询失败:" + arg1);
            }
        });
    }
    private void initView(String msg) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rss_source_recycler);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<RssSource> rssSources = getFacets(msg);
        Log.i("NavDiscovery", rssSources.size() + "::;;;;");

        RssSourceAdapter rssSourceAdapter = new RssSourceAdapter(getContext(),rssSources);
        recyclerView.setAdapter(rssSourceAdapter);
    }
    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public List<RssSource> getFacets(String data) {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        try {
            jsonArray = new JSONArray(data);

        } catch (JSONException e) {
            showToast(e.toString());
        }

        List<RssSource> rssSources = new ArrayList<>();
        rssSources.add(new RssSource());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                RssSource rssSource = new RssSource();
                rssSource.setId(Integer.parseInt(jsonObject.get("id").toString()));
                rssSource.setRssName(jsonObject.get("rssName").toString());
//                        facet.setBackgroundUrl(jsonObject.get("facetImage").toString());
                rssSources.add(rssSource);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        Log.i("NavDiscovery", facets.size() + ":::0");
        return rssSources;
    }

}
