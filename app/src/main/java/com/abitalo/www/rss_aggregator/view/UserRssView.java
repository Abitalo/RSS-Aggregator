package com.abitalo.www.rss_aggregator.view;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.util.MyUtils;
import com.yalantis.phoenix.PullToRefreshView;
import com.yydcdut.sdlv.DragListView;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.List;

/**
 * Created by sangz on 2016/6/7.
 */
public class UserRssView extends Fragment implements SlideAndDragListView.OnListItemLongClickListener, DragListView.OnDragListener, SlideAndDragListView.OnListItemClickListener, SlideAndDragListView.OnMenuItemClickListener, SlideAndDragListView.OnSlideListener, SlideAndDragListView.OnItemDeleteListener, SlideAndDragListView.OnListScrollListener {
    private View view;
    private SlideAndDragListView slideAndDragListView;
    private Menu mMenu;
    private List<ApplicationInfo> mAppList;
    private SlideAndDragListView<ApplicationInfo> mListView;
    private PullToRefreshView mPullToRefreshView;
    private static final String TAG = Fragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_rss, container, false);
        Log.i(TAG, "onCreateView");//match_parent
        initView();
        return view;
    }

    private void initView() {
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh");
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        initData();
        initMenu();
        initUiAndListener();
    }

    private void initData() {
        mAppList = getActivity().getPackageManager().getInstalledApplications(0);
    }

    private void initUiAndListener() {
        mListView = (SlideAndDragListView) view.findViewById(R.id.user_rss_display);
        mListView.setMenu(mMenu);
        mListView.setAdapter(mAdapter);
        mListView.setOnListItemLongClickListener(this);
        mListView.setOnDragListener(this, mAppList);
        mListView.setOnListItemClickListener(this);
        mListView.setOnSlideListener(this);
        mListView.setOnMenuItemClickListener(this);
        mListView.setOnItemDeleteListener(this);
        mListView.setOnListScrollListener(this);
    }

    private void initMenu() {
        mMenu = new Menu(true, true);

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
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.user_rss_option) + 30)
                .setBackground(MyUtils.getDrawable(getContext(), R.drawable.user_rss_top))
                .setText("置顶")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize((int) getResources().getDimension(R.dimen.user_rss_text))
                .build());
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomViewHolder cvh;
            if (convertView == null) {
                cvh = new CustomViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_btn, null);
                cvh.imgLogo = (ImageView) convertView.findViewById(R.id.img_user_rss);
                cvh.txtName = (TextView) convertView.findViewById(R.id.tv_user_rss);
//                cvh.btnClick = (Button) convertView.findViewById(R.id.btn_item_click);
//                cvh.btnClick.setOnClickListener(mOnClickListener);
                convertView.setTag(cvh);
            } else {
                cvh = (CustomViewHolder) convertView.getTag();
            }
            ApplicationInfo item = (ApplicationInfo) this.getItem(position);
            cvh.txtName.setText(item.loadLabel(getActivity().getPackageManager()));
            cvh.imgLogo.setImageDrawable(item.loadIcon(getActivity().getPackageManager()));
//            cvh.btnClick.setText(position + "");
//            cvh.btnClick.setTag(position);
            return convertView;
        }

        class CustomViewHolder {
            public ImageView imgLogo;
            public TextView txtName;
//            public Button btnClick;
        }

        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object o = v.getTag();
                if (o instanceof Integer) {
                    Toast.makeText(getContext(), "button click-->" + ((Integer) o), Toast.LENGTH_SHORT).show();
                }
            }
        };
    };

    @Override
    public void onListItemLongClick(View view, int position) {
//        boolean bool = mListView.startDrag(position);
//        Toast.makeText(getContent(), "onItemLongClick   position--->" + position + "   drag-->" + bool, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "onItemLongClick   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onListItemLongClick   " + position);
    }

    @Override
    public void onDragViewStart(int position) {
        Toast.makeText(getContext(), "onDragViewStart   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewStart   " + position);
    }

    @Override
    public void onDragViewMoving(int position) {
//        Toast.makeText(DemoActivity.this, "onDragViewMoving   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i("yuyidong", "onDragViewMoving   " + position);
    }

    @Override
    public void onDragViewDown(int position) {
        Toast.makeText(getContext(), "onDragViewDown   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewDown   " + position);
    }

    @Override
    public void onListItemClick(View v, int position) {
        Toast.makeText(getContext(), "onItemClick   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onListItemClick   " + position);
    }

    @Override
    public void onSlideOpen(View view, View parentView, int position, int direction) {
        Toast.makeText(getContext(), "onSlideOpen   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onSlideOpen   " + position);
    }

    @Override
    public void onSlideClose(View view, View parentView, int position, int direction) {
        Toast.makeText(getContext(), "onSlideClose   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onSlideClose   " + position);
    }

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        Log.i(TAG, "onMenuItemClick   " + itemPosition + "   " + buttonPosition + "   " + direction);
        switch (direction) {
            case MenuItem.DIRECTION_LEFT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_NOTHING;
                    case 1:
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
        mAppList.remove(position - mListView.getHeaderViewsCount());
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
