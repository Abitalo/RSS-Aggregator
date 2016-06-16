package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.adapter.FacetAdapter;
import com.abitalo.www.rss_aggregator.constants.MessageWhat;
import com.abitalo.www.rss_aggregator.helper.FacetMainViewHelper;
import com.abitalo.www.rss_aggregator.model.Facet;

import java.util.ArrayList;

/**
 * Created by sangz on 2016/5/12.
 * 加载具源分类
 */
public class FacetMainView extends Fragment {
    private View view;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FacetMainViewHelper facetMainViewHelper;
    private ArrayList<Facet> facets;

    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.facet_recycler_main, container, false);
        initView();
        initHandler();
        loadData();
        return view;
    }

    private void loadData() {
        facetMainViewHelper = new FacetMainViewHelper(getContext(), handler);
        facetMainViewHelper.start();
    }

    private void initHandler() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MessageWhat.FACET_LODE_SUCCESS:
                        Bundle bundle = msg.getData();
                        facets = bundle.getParcelableArrayList("facets");
                        continueInitView(facets);
                        break;
                }
            }
        };
    }

    private void continueInitView(ArrayList<Facet> facets) {
        progressBar.setVisibility(View.GONE);
        FacetAdapter facetAdapter = new FacetAdapter(getContext(), facets, this);
        recyclerView.setAdapter(facetAdapter);
    }

    private void initView() {
        progressBar = (ProgressBar) view.findViewById(R.id.facet_loading_bar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) view.findViewById(R.id.discovery_recycler_card_view);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}
