package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abitalo.www.rss_aggregator.R;


/**
 * Created by sangz on 2016/6/7.
 */
public class UserAccountView extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout llExit;
    private LinearLayout llRss;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_account, container, false);
        initView();
        return view;
    }

    private void initView() {

        llExit = (LinearLayout) view.findViewById(R.id.user_exit_);
        llExit.setOnClickListener(this);

        llRss = (LinearLayout) view.findViewById(R.id.user_rss_source);
        llRss.setOnClickListener(this);

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
        }
    }

    private void userRssSource() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, new UserRssView(), "user_rss_view").commit();
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(getActivity().findViewById(R.id.nav_account));
    }

    private void userExit() {
        SharedPreferences mySharedPreferences= getContext().getSharedPreferences("userAuthentication",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("name", "");
        editor.apply();
        getFragmentManager().beginTransaction().replace(R.id.nav_account, new WelcomeNav(), "welcome").commit();
    }
}
