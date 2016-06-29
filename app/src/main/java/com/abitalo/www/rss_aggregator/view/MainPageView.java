package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abitalo.www.rss_aggregator.R;

/**
 * Created by sangz on 2016/6/10.
 */
public class MainPageView extends Fragment {
    private View view;
    private Handler handler;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_page, container, false);
        initView();
        initHandler();
        getData();
        return view;
    }

    private void initHandler() {
        handler = new Handler(){
          public void handMessage(Message msg){
              switch (msg.what){
                  
              }
          }
        };
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.main_page_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getData() {


    }
}
