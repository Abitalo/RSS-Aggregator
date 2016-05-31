package com.abitalo.www.rss_aggregator.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.abitalo.www.rss_aggregator.model.RSSFeed;
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
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Messages.RSS_PARSE_SUCCESS:
                        final Bundle bundle = msg.getData();
                        ArrayList<RSSItem> list = bundle.getParcelableArrayList("list");
                        if (null == list) {
                            Snackbar.make(view, "URL is illegal.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.e("LOGCAT", "dajskdjaksljdlkasjdlkasjdlksajfkdjshfsadjhgkjhgsdj gkjd");
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
                        Snackbar.make(view, "URL is illegal.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                }
            }
        };
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
}
