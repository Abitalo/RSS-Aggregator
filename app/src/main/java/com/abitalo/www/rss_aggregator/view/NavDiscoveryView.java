package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by sangzhenya on 2016/5/10.
 * 添加内容界面
 */
public class NavDiscoveryView extends Fragment {
    View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_discovery, container, false);
        initView();
        return view;
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.discovery_recycler_card_view);
        RecyclerView recyclerViewSmall = (RecyclerView) view.findViewById(R.id.discovery_recycler_card_view_small);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSmall.setLayoutManager(new GridLayoutManager(getContext(), 3));

//        List<Facet> facets = getFacets();
        Log.i("NavDiscovery", "-----------------------------------------");
        List<Facet> facets = new ArrayList<>();
        List<Facet> facetsSmall = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            Facet facet = new Facet();
            facet.setFacetName("hhh"+i);
            if (i < 6){
                facets.add(facet);
            }
            facetsSmall.add(facet);
        }
        FacetAdapter facetListAdapter = new FacetAdapter(getContext(), facets, 1);
        FacetAdapter facetListAdapterSmall = new FacetAdapter(getContext(), facetsSmall, 3);
        recyclerView.setAdapter(facetListAdapter);
        recyclerViewSmall.setAdapter(facetListAdapterSmall);

    }

    public JSONArray getJSONFacets() {
        JSONArray jsonArray;
        BmobQuery query = new BmobQuery("facet");
        query.findObjects(getContext(), new FindCallback() {
            @Override
            public void onSuccess(JSONArray arg0) {
//                jsonArray = arg0;
            }


            @Override
            public void onFailure(int arg0, String arg1) {
                showToast("查询失败:" + arg1);
            }
        });
        return null;
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*public List<Facet> getFacets() {
        getJSONFacets();
        List<Facet> facets = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            try {
                Facet facet = new Facet();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                facet.setId(Integer.parseInt(jsonObject.get("id").toString()));
                facet.setFacetName(jsonObject.get("facetName").toString());
//                        facet.setBackgroundUrl(jsonObject.get("facetImage").toString());

                facets.add(facet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i("NavDiscovery", facets.size() + ":::0");
        return facets;
    }*/
}
