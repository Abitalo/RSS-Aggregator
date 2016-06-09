package com.abitalo.www.rss_aggregator.view;

import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.adapter.FacetAdapter;
import com.abitalo.www.rss_aggregator.model.Facet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;

/**
 * Created by sangzhenya on 2016/5/10.
 * APP右侧展示源信息的界面
 */
public class NavDiscoveryView extends Fragment {
    private View view = null;

    private FragmentManager fragmentManager;
    private DrawerLayout drawer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nav_discovery, container, false);
        initView();
        return view;
    }

    private void initView() {
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.nav_discovery_main, new FacetMainView(), "facet_view").commit();
        EditText etSearchInput = (EditText) view.findViewById(R.id.search_input);
        ImageView ivSearchButton = (ImageView) view.findViewById(R.id.search_icon);
        ImageView ivBackButton = (ImageView) view.findViewById(R.id.back_icon);

        etSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    submitText();
                }
                return false;
            }
        });

        ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitText();
            }
        });
        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLast();
            }
        });
    }

    private void backToLast() {
        Fragment fragment = fragmentManager.findFragmentByTag("facet_view");
        if (fragment != null && fragmentManager.findFragmentByTag("facet_view").isVisible()) {
            drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(getActivity().findViewById(R.id.discovery_nav_view));
        } else {
            fragmentManager.beginTransaction().replace(R.id.nav_discovery_main, new FacetMainView(), "facet_view").commit();
        }
    }

    private void submitText() {
        Toast.makeText(getContext(), "here you are", Toast.LENGTH_SHORT).show();
    }

}
