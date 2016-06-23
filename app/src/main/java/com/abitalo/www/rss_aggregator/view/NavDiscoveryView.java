package com.abitalo.www.rss_aggregator.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
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
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by sangzhenya on 2016/5/10.
 * APP右侧展示源信息的界面
 */
public class NavDiscoveryView extends Fragment {
    private View view = null;
    private MaterialEditText etSearchInput;

    private FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private ImageView ivSearchButton;
    private ImageView ivBackButton;


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


        etSearchInput = (MaterialEditText) view.findViewById(R.id.search_input);

        ivSearchButton = (ImageView) view.findViewById(R.id.search_icon);
        ivBackButton = (ImageView) view.findViewById(R.id.back_icon);

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
        String inputText = etSearchInput.getText().toString();
        if (inputText.equals("")){
//            Toast.makeText(getContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
            etSearchInput.setError("请输入搜索内容");
        }else if (inputText.indexOf('.') == -1) {
//            Toast.makeText(getContext(), "here you are", Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.nav_discovery_main, RssSourceView.newInstance(inputText, RssSourceView.SEARCH), "rss_source").commit();
        } else {
            if (!inputText.startsWith("http://")) {
                inputText = "http://" + inputText;
            }
            getFragmentManager().beginTransaction().replace(R.id.fragment_content,
                    RSSListView.newInstance(inputText,0), "fragment_view").commit();
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(getActivity().findViewById(R.id.discovery_nav_view));
        }
    }

}
