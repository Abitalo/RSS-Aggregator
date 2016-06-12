package com.abitalo.www.rss_aggregator.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.support.v4.app.FragmentTransaction;
=======
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.WebActivity;
import com.abitalo.www.rss_aggregator.constants.Messages;
import com.abitalo.www.rss_aggregator.listener.ItemOnClickListener;
<<<<<<< HEAD
import com.abitalo.www.rss_aggregator.model.RSSFeed;
=======
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
import com.abitalo.www.rss_aggregator.model.RSSItem;
import com.abitalo.www.rss_aggregator.presenter.RSSParser;
import com.abitalo.www.rss_aggregator.util.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sangzhenya on 2016/5/10.
 * 添加内容界面
 */
public class RSSListView extends Fragment {
<<<<<<< HEAD
    View view = null;
    private RecyclerView recyclerView = null;
    private RSSFeed rssFeed =null;
    private String url = null;
    private ListAdapter listAdapter = null;
    private Handler handler = null;
    private ProgressBar progressBar = null;
    private Fragment self;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_rss_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.rss_loading_bar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) view.findViewById(R.id.rss_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        self = this;
=======
    private static final String RSSURL = "url";
    private View view = null;
    private String url = null;
    private RecyclerView recyclerView = null;
    private SwipeRefreshLayout refreshLayout = null;
    private ListAdapter listAdapter = null;
    private Handler handler = null;
    private ProgressBar progressBar = null;
    private RSSParser currentRSS = null;

    public static Fragment newInstance(String arg){
        RSSListView fragment = new RSSListView();
        Bundle bundle = new Bundle();
        bundle.putString(RSSURL,arg);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_rss_list, container, false);
        initView();
        initHandler();
        openRSS();
        return view;
    }

    private void initView(){
        progressBar = (ProgressBar) view.findViewById(R.id.rss_loading_bar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) view.findViewById(R.id.rss_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.rss_list_pull_down);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RSSParser(url,handler).start();
                Log.e("LOGCAT","啦啦1");
            }
        });
        refreshLayout.setColorSchemeResources(
                R.color.colorPrimary
        );
    }

    private void initHandler(){
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Messages.RSS_PARSE_SUCCESS:
<<<<<<< HEAD
                        final Bundle bundle = msg.getData();
                        ArrayList<RSSItem> list = bundle.getParcelableArrayList("list");
                        if (null == list) {
                            Snackbar.make(view, "URL is illegal.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.e("LOGCAT", "dajskdjaksljdlkasjdlkasjdlksajfkdjshfsadjhgkjhgsdj gkjd");
=======
                        releaseRSSParser();
                        progressBar.setVisibility(View.GONE);
                        if(null !=refreshLayout){
                            refreshLayout.setRefreshing(false);
                        }
                        Log.e("LOGCAT","获取RSS完毕");
                        final Bundle bundle = msg.getData();
                        ArrayList<RSSItem> list = bundle.getParcelableArrayList("list");
                        if (null == list) {
                            Snackbar.make(view, "Can not get data.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            if (null !=bundle.getString("title")){
                                try {
                                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(bundle.getString("title"));
                                }catch (Exception exception){
                                    Log.e("RssListView", "Some thing wrong");
                                }
                            }
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
                            listAdapter = new ListAdapter(list);
                            listAdapter.setListener(new ItemOnClickListener() {
                                @Override
                                public void onItemClick(View view, HashMap item) {
                                    Intent intent = new Intent(getActivity(),WebActivity.class);
                                    intent.putExtra("item",item);
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(listAdapter);
                        }
                        break;
                    case Messages.URL_ILLEAGEL:
<<<<<<< HEAD
=======
                        releaseRSSParser();
                        progressBar.setVisibility(View.GONE);
                        if(null !=refreshLayout){
                            refreshLayout.setRefreshing(false);
                        }
                        Log.e("LOGCAT","获取RSS失败");
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
                        Snackbar.make(view, "URL is illegal.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                }
            }
        };
<<<<<<< HEAD
        new RSSParser("https://www.zhihu.com/rss",handler).start();
        return view;
    }

/*    public void switchContent(Fragment to) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in,android.R.anim.slide_out_right);
            if (!to.isAdded())    // 先判断是否被add过
                transaction.hide(this).add(R.id.fragment_content, to).commit();
            else
                transaction.hide(this).show(to).commit();
            getFragmentManager().executePendingTransactions();
    }*/
=======
    }

    public void releaseRSSParser(){
//        currentRSS.stopPulling();
        currentRSS = null;
    }

    private void openRSS(){
        url = getArguments().getString(RSSURL);
        if(null != currentRSS){
            currentRSS.stopPulling();
        }
        if(null != refreshLayout) {
            refreshLayout.setRefreshing(true);
        }
        currentRSS = new RSSParser(url,handler);
        currentRSS.start();
    }
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
}
