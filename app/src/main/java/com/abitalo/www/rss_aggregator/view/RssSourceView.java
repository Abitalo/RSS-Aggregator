package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.adapter.RssSourceAdapter;
import com.abitalo.www.rss_aggregator.constants.MessageWhat;
import com.abitalo.www.rss_aggregator.helper.RssSourceViewHelper;
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
 * 源信息列表展示
 */
public class RssSourceView extends Fragment {
    private View view;
    private int facetId;
    private Handler handler;
    private RssSourceViewHelper rssSourceViewHelper;
    private ArrayList<RssSource> rssSources;
    private RecyclerView recyclerView;

    private ProgressBar progressBar;



    public RssSourceView() {
        this(1);
    }

    public RssSourceView(int facetId) {
        this.facetId = facetId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.rss_source_main, container, false);
        initView();
        initHandler();
        loadData();
        return view;
    }

    private void loadData() {
        rssSourceViewHelper = new RssSourceViewHelper(getContext(),handler,facetId);
        rssSourceViewHelper.start();
    }

    private void initHandler() {
        handler = new android.os.Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MessageWhat.RSS_SOURCE_LOAD_SUCCESS:
                        Bundle bundle = msg.getData();
                        rssSources = bundle.getParcelableArrayList("rssSources");
                        continueInitView(rssSources);
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    private void continueInitView(ArrayList<RssSource> rssSources) {
        progressBar.setVisibility(View.GONE);
        RssSourceAdapter rssSourceAdapter = new RssSourceAdapter(getContext(), rssSources, this);
        recyclerView.setAdapter(rssSourceAdapter);

    }

    private void initView() {
        progressBar = (ProgressBar) view.findViewById(R.id.inner_rss_loading_bar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) view.findViewById(R.id.rss_source_recycler);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("RssSourceView", "here you are");
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.nav_discovery_main, new FacetMainView(), "facet").commit();
                    return true;
                }
                return false;
            }
        });
    }




}
