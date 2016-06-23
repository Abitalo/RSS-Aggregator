package com.abitalo.www.rss_aggregator.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.entity.RssSource;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by sangz on 2016/5/12.
 * 具体源的列表的适配器
 */
public class RssSourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE_TITLE = 866;
    private static final int TYPE_LIST = 322;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<RssSource> rssSources;

    public RssSourceAdapter(List<RssSource> rssSources){
        this.rssSources = rssSources;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_LIST){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_recycler_item, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
            view.setOnClickListener(this);
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
            holder.itemView.setTag(R.id.tag_url,rssSources.get(position).getRssUrl());
            holder.itemView.setTag(R.id.tag_facetId,rssSources.get(position).getFacetId());

            ((ViewHolder)holder).tvRssSourceTitle.setText(rssSources.get(position).getRssName());

            ((ViewHolder)holder).ivRssSourceAdd.setTag(rssSources.get(position).getRssUrl());
            ((ViewHolder)holder).ivRssSourceAdd.setOnClickListener(this);

            ((ViewHolder) holder).ivRssSourceIcon.setImageURI(Uri.parse(rssSources.get(position).getRssIcon()));

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

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvRssSourceTitle;
        private SimpleDraweeView ivRssSourceIcon;
        private TextView tvRssSourceReader;
        private ImageView ivRssSourceAdd;

        public ViewHolder(View itemView) {
            super(itemView);

            tvRssSourceTitle = (TextView) itemView.findViewById(R.id.rss_source_title);
            tvRssSourceReader = (TextView) itemView.findViewById(R.id.rss_source_reader);
            ivRssSourceIcon = (SimpleDraweeView) itemView.findViewById(R.id.rss_source_icon);
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
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
}
