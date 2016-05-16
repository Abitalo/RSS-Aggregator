package com.abitalo.www.rss_aggregator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.model.RssSource;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by sangz on 2016/5/12.
 * 具体源的列表的适配器
 */
public class RssSourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_TITLE = 866;
    private static final int TYPE_LIST = 322;

    private Context context;
    private List<RssSource> rssSources;

    public RssSourceAdapter(Context context, List<RssSource> rssSources){
        this.rssSources = rssSources;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_LIST){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_recycler_item, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
            return new ViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_recycler_title, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
            return new TextViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder)holder).tvRssSourceTitle.setText(rssSources.get(position).getRssName());
//            ((ViewHolder)holder).tvRssSourceTitle.setTag(rssSources.get(position).getRssUrl());
            ((ViewHolder)holder).tvRssSourceTitle.setOnClickListener(this);
//            ((ViewHolder)holder).ivRssSourceAdd.setTag(rssSources.get(position).getRssUrl());
            ((ViewHolder)holder).ivRssSourceAdd.setOnClickListener(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_TITLE;
        }else{
            return TYPE_LIST;
        }
        
    }

    @Override
    public int getItemCount() {
        return rssSources.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rss_source_title){
            // TODO: 2016/5/16 点击标题的事件
            Toast.makeText(context, "标题被点击了", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.rss_add_source){
            //// TODO: 2016/5/16 点击添加的按钮的操作
            Toast.makeText(context, "添加按钮被点击了", Toast.LENGTH_SHORT).show();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvRssSourceTitle;
        private ImageView ivRssSourceIcon;
        private TextView tvRssSourceReader;
        private ImageView ivRssSourceAdd;

        public ViewHolder(View itemView) {
            super(itemView);

            tvRssSourceTitle = (TextView) itemView.findViewById(R.id.rss_source_title);
            tvRssSourceReader = (TextView) itemView.findViewById(R.id.rss_source_reader);
            ivRssSourceIcon = (ImageView) itemView.findViewById(R.id.rss_source_icon);
            ivRssSourceAdd = (ImageView) itemView.findViewById(R.id.rss_add_source);

        }
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSearchTip;

        public TextViewHolder(View itemView) {
            super(itemView);
            tvSearchTip = (TextView) itemView.findViewById(R.id.rss_search_title);
        }
    }
}
