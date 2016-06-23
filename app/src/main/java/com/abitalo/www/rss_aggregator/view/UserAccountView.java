package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.IconEditActivity;
import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.constants.MessageWhat;
import com.abitalo.www.rss_aggregator.helper.UserAccountViewHelper;

import java.util.ArrayList;


/**
 * Created by sangz on 2016/6/7.
 */
public class UserAccountView extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tvUserName;
    private TextView tvEmail;
    private ArrayList<String> userInfo;
    private ImageView iconImage;
    private Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_account, container, false);
        initView();
        initHandler();
        loadData();
        return view;
    }

    private void loadData() {
        UserAccountViewHelper userAccountViewHelper = new UserAccountViewHelper(getContext(), handler);
        userAccountViewHelper.start();
    }

    private void initHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MessageWhat.USER_INFO_LOAD_SUCCESS:
                        Bundle bundle = msg.getData();
                        userInfo = bundle.getStringArrayList("userInfo");
                        changeView();
                        break;
                }
            }
        };
    }

    private void changeView() {
        tvUserName.setText(userInfo.get(0));
        tvEmail.setText(userInfo.get(1));
    }

    private void initView() {
        LinearLayout llExit = (LinearLayout) view.findViewById(R.id.user_exit_);
        llExit.setOnClickListener(this);

        LinearLayout llRss = (LinearLayout) view.findViewById(R.id.user_rss_source);
        llRss.setOnClickListener(this);

        LinearLayout llMainPage = (LinearLayout) view.findViewById(R.id.ll_main_page);
        llMainPage.setOnClickListener(this);


        LinearLayout llAllArticle = (LinearLayout) view.findViewById(R.id.all_article);
        llAllArticle.setOnClickListener(this);

        LinearLayout llStarArticle = (LinearLayout) view.findViewById(R.id.star_article);
        llStarArticle.setOnClickListener(this);

        LinearLayout llSaveArticle = (LinearLayout) view.findViewById(R.id.save_article);
        llSaveArticle.setOnClickListener(this);

        iconImage=(ImageView)view.findViewById(R.id.user_icon);
        iconImage.setOnClickListener(this);

        tvUserName = (TextView) view.findViewById(R.id.tv_user_info_name);
        tvEmail = (TextView) view.findViewById(R.id.tv_user_info_email);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_exit_:
                userExit();
                break;
            case R.id.user_rss_source:
                userRssSource();
                break;
            case R.id.ll_main_page:
                mainPage();
                break;
            case R.id.all_article:
                mainPage();
                break;
            case R.id.star_article:
                favoriteArticle();
                break;
            case R.id.save_article:
                mainPage();
            case R.id.user_icon:
                editIcon();
                break;
        }
    }

    private void mainPage() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_content,RSSListView.newInstance("https://www.zhihu.com/rss",11), "fragment_view").commit();
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(getActivity().findViewById(R.id.nav_account));
    }

    private void userRssSource() {
//        ((RSSListView)getFragmentManager().findFragmentByTag("fragment_view")).releaseRSSParser();
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, new UserRssView(), "fragment_view").commit();
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(getActivity().findViewById(R.id.nav_account));
    }

    private void favoriteArticle(){
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, new UserRssView(), "fragment_view").commit();
        ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).closeDrawer(getActivity().findViewById(R.id.nav_account));

    }

    private void userExit() {
        SharedPreferences mySharedPreferences= getContext().getSharedPreferences("userAuthentication",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("name", "");
        editor.apply();
        getFragmentManager().beginTransaction().replace(R.id.nav_account, new WelcomeNav(), "welcome").commit();
    }

    private void editIcon(){
        Intent intent=new Intent();
        intent.setClass(getActivity(),IconEditActivity.class);
        startActivity(intent);
    }
}
