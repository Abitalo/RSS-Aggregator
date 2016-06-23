package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.abitalo.www.rss_aggregator.helper.RssSourceViewSearchHelper;
import com.abitalo.www.rss_aggregator.helper.UserRssEditHelper;
import com.abitalo.www.rss_aggregator.entity.RssSource;

import java.util.ArrayList;

/**
 * Created by sangz on 2016/5/12.
 * 源信息列表展示
 */
public class RssSourceView extends Fragment {
    private View view;
    private int facetId;
    private Handler handler;
    private ArrayList<RssSource> rssSources;
    private RecyclerView recyclerView;

    public static int FACET = 0;
    public static int SEARCH = 1;
    private int option;
    private ProgressBar progressBar;
    private String keyword;

    private View clickedView;


    public static Fragment newInstance(String keyword,Integer option){
        RssSourceView rssSourceView = new RssSourceView();
        rssSourceView.keyword = keyword;
        rssSourceView.option = option;
        return rssSourceView;
    }

    public static Fragment newInstance(int facetId, Integer option){
        RssSourceView rssSourceView = new RssSourceView();
        rssSourceView.facetId = facetId;
        rssSourceView.option = option;
        return rssSourceView;
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
        if (FACET == option){
            RssSourceViewHelper rssSourceViewHelper = new RssSourceViewHelper(getContext(), handler, facetId);
            rssSourceViewHelper.start();
        }else if(SEARCH == option){
            RssSourceViewSearchHelper rssSourceViewSearchHelper = new RssSourceViewSearchHelper(getContext(), handler, keyword);
            rssSourceViewSearchHelper.start();
        }

    }

    private void initHandler() {
        handler = new android.os.Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MessageWhat.RSS_SOURCE_LOAD_SUCCESS:
                        Bundle bundle = msg.getData();
                        rssSources = bundle.getParcelableArrayList("rssSources");
                        continueInitView();
                        break;
                    case MessageWhat.NOT_LOGIN:
                        clickedView.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(),"请登录后订阅", Toast.LENGTH_SHORT).show();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }


    private void continueInitView() {
        progressBar.setVisibility(View.GONE);
        RssSourceAdapter rssSourceAdapter = new RssSourceAdapter(rssSources);
        recyclerView.setAdapter(rssSourceAdapter);
        rssSourceAdapter.setOnItemClickListener(new RssSourceAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, String data) {
                //订阅
                if (v.getId() == R.id.rss_add_source){
                    clickedView = v;
                    v.setVisibility(View.GONE);

                    UserRssEditHelper userRssEditHelper = new UserRssEditHelper(getContext(), v.getTag().toString(), handler);
                    userRssEditHelper.getUserId(UserRssEditHelper.ADD);
//                    Toast.makeText(getContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
                }else {
                    //查看
                    getFragmentManager().beginTransaction().replace(R.id.fragment_content,RSSListView.newInstance(v.getTag(R.id.tag_url).toString(),(Integer)v.getTag(R.id.tag_facetId)), "fragment_view").addToBackStack(null).commit();

                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(getActivity().findViewById(R.id.discovery_nav_view));
                }
            }
        });

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
//        Log.i("RssSourceView", "here you are");
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
