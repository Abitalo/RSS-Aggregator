package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abitalo.www.rss_aggregator.R;

/**
 * Created by sangzhenya on 2016/5/9.
 * 临时展示登录之后内容的界面
 */
public class MainContentDemo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_content_demo, container, false);
    }
}
