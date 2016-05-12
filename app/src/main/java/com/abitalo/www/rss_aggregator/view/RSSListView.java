package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.adapter.FacetAdapter;
import com.abitalo.www.rss_aggregator.constants.Messages;
import com.abitalo.www.rss_aggregator.model.Facet;
import com.abitalo.www.rss_aggregator.model.RSSFeed;
import com.abitalo.www.rss_aggregator.model.RSSItem;
import com.abitalo.www.rss_aggregator.presenter.RSSParser;
import com.abitalo.www.rss_aggregator.util.ListAdapter;

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
public class RSSListView extends Fragment {
    View view = null;
    private RecyclerView recyclerView = null;
    private RSSFeed rssFeed =null;
    private String url = null;
    private ListAdapter listAdapter = null;
    private Handler handler = null;
    private ProgressBar progressBar = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.conten_rss_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.rss_loading_bar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) view.findViewById(R.id.rss_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Messages.RSS_PARSE_SUCCESS:
                        Bundle bundle = msg.getData();
                        ArrayList<RSSItem> list = bundle.getParcelableArrayList("list");
                        if (null == list) {
                            Snackbar.make(view, "URL is illegal.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.e("LOGCAT", "dajskdjaksljdlkasjdlkasjdlksajfkdjshfsadjhgkjhgsdj gkjd");
                            listAdapter = new ListAdapter(list);
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
        new RSSParser("http://rss.sina.com.cn/tech/rollnews.xml",handler).start();
        return view;
    }

}
