package com.abitalo.www.rss_aggregator.view;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.constants.Conf;
import com.abitalo.www.rss_aggregator.constants.MessageWhat;
import com.abitalo.www.rss_aggregator.entity.UserData;
import com.abitalo.www.rss_aggregator.helper.UserRssEditHelper;
import com.abitalo.www.rss_aggregator.helper.UserRssSourceHelper;
import com.abitalo.www.rss_aggregator.entity.RssSource;
import com.abitalo.www.rss_aggregator.util.MyUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yydcdut.sdlv.DragListView;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by sangz on 2016/6/7.
 * User Rss View
 */
public class UserRssView extends Fragment implements SlideAndDragListView.OnListItemLongClickListener, DragListView.OnDragListener, SlideAndDragListView.OnListItemClickListener, SlideAndDragListView.OnMenuItemClickListener, SlideAndDragListView.OnSlideListener, SlideAndDragListView.OnItemDeleteListener, SlideAndDragListView.OnListScrollListener {
    private View view;
    private ProgressBar progressBar;
    private List<RssSource> rssSourceList;
    private SlideAndDragListView<RssSource> mListView;
//    private PullToRefreshView mPullToRefreshView;
    private ArrayList<Menu> mMenuList;
    private Handler handler;
    private String userdataId;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_rss, container, false);
        initView();
        initHandler();
        loadData();
        context=getContext();
        return view;
    }

    private void initHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MessageWhat.RSS_SOURCE_LOAD_SUCCESS:
                        Bundle bundle = msg.getData();
                        rssSourceList = bundle.getParcelableArrayList("rssSources");
                        userdataId=bundle.getString("userdataId");
                        continueInitView();
                        break;
                }
            }
        };
    }

    private void continueInitView() {
        progressBar.setVisibility(View.GONE);
        initUiAndListener();
    }

    private void initView() {
        try {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("用户收藏");
        }catch (Exception exception){
            Log.e("RssListView", "Some thing wrong");
        }
        progressBar = (ProgressBar) view.findViewById(R.id.user_rss_loading);
        progressBar.setVisibility(View.VISIBLE);

       /* mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 50);
            }
        });*/
        initMenu();
    }

    private void loadData() {
        UserRssSourceHelper userRssSourceHelper = new UserRssSourceHelper(getContext(), handler);
        userRssSourceHelper.start();
    }

    private void initUiAndListener() {
        mListView = (SlideAndDragListView) view.findViewById(R.id.user_rss_display);
        mListView.setMenu(mMenuList);
        mAdapter.notifyDataSetChanged();
        mListView.setAdapter(mAdapter);
        mListView.setOnListItemLongClickListener(this);
        mListView.setOnDragListener(this, rssSourceList);
        mListView.setOnListItemClickListener(this);
        mListView.setOnSlideListener(this);
        mListView.setOnMenuItemClickListener(this);
        mListView.setOnItemDeleteListener(this);
        mListView.setOnListScrollListener(this);
    }

    private void initMenu() {
        mMenuList = new ArrayList<>(2);

        Menu mMenu = new Menu(false, false, 0);
/*        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.user_rss_option))
                .setBackground(MyUtils.getDrawable(getContext(), R.drawable.user_rss_delete))
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setIcon(getResources().getDrawable(R.mipmap.delete))
                .build());*/
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.user_rss_option) + 30)
                .setBackground(MyUtils.getDrawable(getContext(), R.drawable.user_rss_delete))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize((int) getResources().getDimension(R.dimen.user_rss_text))
                .build());
        /*mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.user_rss_option) + 30)
                .setBackground(MyUtils.getDrawable(getContext(), R.drawable.user_rss_top))
                .setText("置顶")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize((int) getResources().getDimension(R.dimen.user_rss_text))
                .build());*/

        Menu mMenu1 = new Menu(false, false, 1);

        mMenu1.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.user_rss_option) + 30)
                .setBackground(MyUtils.getDrawable(getContext(), R.drawable.user_rss_delete))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize((int) getResources().getDimension(R.dimen.user_rss_text))
                .build());
       /* mMenu1.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.user_rss_option) + 30)
                .setBackground(MyUtils.getDrawable(getContext(), R.drawable.user_rss_top))
                .setText("取消置顶")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize((int) getResources().getDimension(R.dimen.user_rss_text))
                .build());*/
        mMenuList.add(mMenu);
        mMenuList.add(mMenu1);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return rssSourceList.size();
        }

        @Override
        public Object getItem(int position) {
            return rssSourceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < 10){
                return 1;
            }else {
                return 0;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomViewHolder cvh;
            if (convertView == null) {
                cvh = new CustomViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_btn, null);
                cvh.imgLogo = (SimpleDraweeView) convertView.findViewById(R.id.img_user_rss);
                cvh.txtName = (TextView) convertView.findViewById(R.id.tv_user_rss);
                convertView.setTag(cvh);
            } else {
                cvh = (CustomViewHolder) convertView.getTag();
            }
            cvh.txtName.setText(rssSourceList.get(position).getRssName());
            cvh.imgLogo.setImageURI(Uri.parse(rssSourceList.get(position).getRssIcon()));
            return convertView;
        }

        class CustomViewHolder {
            public SimpleDraweeView imgLogo;
            public TextView txtName;
        }

    };

    @Override
    public void onListItemLongClick(View view, int position) {
//        Toast.makeText(getContext(), "onItemLongClick   position--->" + position, Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onListItemLongClick   " + position);
    }

    @Override
    public void onDragViewStart(int position) {
//        Toast.makeText(getContext(), "onDragViewStart   position--->" + position, Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onDragViewStart   " + position);
    }

    @Override
    public void onDragViewMoving(int position) {
//        Toast.makeText(DemoActivity.this, "onDragViewMoving   position--->" + position, Toast.LENGTH_SHORT).show();
//        Log.i("yuyidong", "onDragViewMoving   " + position);
    }

    @Override
    public void onDragViewDown(int position) {
//        Toast.makeText(getContext(), "onDragViewDown   position--->" + position, Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onDragViewDown   " + position);
    }

    @Override
    public void onListItemClick(View v, int position) {
        final String url=rssSourceList.get(position).getRssUrl();
        BmobQuery<RssSource> bmobQuery=new BmobQuery<>("rss_source");
        bmobQuery.addWhereEqualTo("rssUrl",url);
        bmobQuery.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try{
                    final Integer facetId=(Integer)jsonArray.getJSONObject(0).get("facetId");
                    BmobQuery<UserData> query=new BmobQuery<>();
                    query.getObject(context, userdataId, new GetListener<UserData>() {
                        @Override
                        public void onSuccess(UserData userData) {
                            userData.getTendencies().set(facetId,userData.getTendencies().get(facetId)+ Conf.CHECK_SUBSCRIBED_RSS);
                            userData.update(context);
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                    getFragmentManager().beginTransaction().replace(R.id.fragment_content,RSSListView.newInstance(url,facetId), "fragment_view").commit();
                }catch (JSONException e){
                    Log.i("abitalo.UserRssView",e.getMessage());
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
//        Log.i(TAG, "onListItemClick   " + position);
    }

    @Override
    public void onSlideOpen(View view, View parentView, int position, int direction) {
//        Toast.makeText(getContext(), "onSlideOpen   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onSlideOpen   " + position);
    }

    @Override
    public void onSlideClose(View view, View parentView, int position, int direction) {
//        Toast.makeText(getContext(), "onSlideClose   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onSlideClose   " + position);
    }

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
//        Log.i(TAG, "onMenuItemClick   " + itemPosition + "   " + buttonPosition + "   " + direction);
        int viewType = mAdapter.getItemViewType(itemPosition);
        switch (viewType) {
            case 0:
                return clickMenuBtn0(buttonPosition, direction);
            case 1:
                return clickMenuBtn1(buttonPosition, direction);
            default:
                return Menu.ITEM_NOTHING;
        }
    }

    private int clickMenuBtn0(int buttonPosition, int direction) {
        switch (direction) {
            case MenuItem.DIRECTION_LEFT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_SCROLL_BACK;
                }
                break;
            case MenuItem.DIRECTION_RIGHT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                    case 1:
                        return Menu.ITEM_SCROLL_BACK;
                }
        }
        return Menu.ITEM_NOTHING;
    }

    private int clickMenuBtn1(int buttonPosition, int direction) {
        switch (direction) {
            case MenuItem.DIRECTION_LEFT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_SCROLL_BACK;
                }
                break;
            case MenuItem.DIRECTION_RIGHT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                    case 1:
                        return Menu.ITEM_SCROLL_BACK;
                }
        }
        return Menu.ITEM_NOTHING;
    }

    @Override
    public void onItemDelete(View view, int position) {
        UserRssEditHelper userRssEditHelper = new UserRssEditHelper(getContext(), rssSourceList.get(position).getRssUrl(), null);
        userRssEditHelper.getUserId(UserRssEditHelper.DELETE);
        rssSourceList.remove(position - mListView.getHeaderViewsCount());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SlideAndDragListView.OnListScrollListener.SCROLL_STATE_IDLE:
                break;
            case SlideAndDragListView.OnListScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                break;
            case SlideAndDragListView.OnListScrollListener.SCROLL_STATE_FLING:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }



/*    private void initView1(){
        List<RssSource> rssSources = new ArrayList<>();
        for (int i = 0; i< 16; i++) {
            RssSource rssSource = new RssSource();
            rssSource.setId(i);
            rssSource.setRssName("rss"+i);
            rssSources.add(rssSource);
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.user_rss_list);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        UserRssAdapter userRssAdapter = new UserRssAdapter(getContext(),rssSources);
        recyclerView.setAdapter(userRssAdapter);
    }*/
}
