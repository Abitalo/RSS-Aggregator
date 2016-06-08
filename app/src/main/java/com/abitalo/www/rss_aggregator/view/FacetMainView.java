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
import com.abitalo.www.rss_aggregator.model.Facet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

/**
 * Created by sangz on 2016/5/12.
 * 加载具源分类
 */
public class FacetMainView extends Fragment {
    private View view;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.facet_recycler_main, container, false);
        getJSONFacets();
        return view;
    }

    private void initView(String data) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.discovery_recycler_card_view);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<Facet> facets = getFacets(data);

        FacetAdapter facetListAdapter = new FacetAdapter(getContext(), facets, this);
        recyclerView.setAdapter(facetListAdapter);

    }

    public void getJSONFacets() {
        BmobQuery query = new BmobQuery("facet");
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray arg0) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", arg0.toString());
                message.setData(bundle);
                message.what = 1;
                handler.sendMessage(message);
            }
            @Override
            public void onFailure(int arg0, String arg1) {
                showToast("查询失败:" + arg1);
            }
        });
    }


    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public List<Facet> getFacets(String data) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonArray = new JSONArray(data);

        } catch (JSONException e) {
            showToast(e.toString());
        }

        List<Facet> facets = new ArrayList<>();
        facets.add(new Facet());
        assert jsonArray != null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                Facet facet = new Facet();
                facet.setId(Integer.parseInt(jsonObject.get("id").toString()));
                facet.setFacetName(jsonObject.get("facetName").toString());
//                        facet.setBackgroundUrl(jsonObject.get("facetImage").toString());
                facets.add(facet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return facets;
    }
}
