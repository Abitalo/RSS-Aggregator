package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.presenter.AccountPresenter;
import com.abitalo.www.rss_aggregator.util.MD5Encrypt;

/**
 * Created by Lancelot on 2016/5/3.
 * 左划拉出的欢迎界面
 */
public class WelcomeNav extends Fragment implements View.OnClickListener {
    View view = null;
    Button btnLogin = null;
    Button btnAddContent = null;
    DrawerLayout drawer = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_welcome, container, false);
        btnLogin = (Button) view.findViewById(R.id.welcomeLogin);
        if(null == view) {
            Log.e("LOGCAT", "操你爸！！！！！！！！！！！！！！！！！！！！！");
        }
        if(null == btnLogin) {
            Log.e("LOGCAT", "操你妈！！！！！！！！！！！！！！！！！！！！！");
        }
        btnAddContent = (Button) view.findViewById(R.id.addContent);
        initialView();
        return view;
    }

    private void initialView(){
        btnLogin.setOnClickListener(this);
        btnAddContent.setOnClickListener(this);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcomeLogin:
                gotoLoginPage();
                break;
            case R.id.addContent:
                addContent();
                break;
        }
    }

    private void addContent() {
        Log.i("welcome", "here you are");
        drawer.closeDrawer(getActivity().findViewById(R.id.nav_account));
        drawer.openDrawer(getActivity().findViewById(R.id.discovery_nav_view));
    }

    private void gotoLoginPage() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_content, new SignInView(), "sign_in").commit();
        drawer.closeDrawer(getActivity().findViewById(R.id.nav_account));
    }
}
