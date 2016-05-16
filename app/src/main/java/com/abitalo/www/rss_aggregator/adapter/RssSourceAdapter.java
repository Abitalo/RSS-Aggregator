package com.abitalo.www.rss_aggregator.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.model.RssSource;

import java.util.List;

/**
 * Created by sangz on 2016/5/12.
 */
public class RssSourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<RssSource> rssSources;

    public RssSourceAdapter(List<RssSource> rssSources){
        this.rssSources = rssSources;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_recycler_item, parent, false);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return rssSources.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView rssSourceTitle;

        public ViewHolder(View itemView) {
            super(itemView);


        }
    }
}
