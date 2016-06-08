package com.abitalo.www.rss_aggregator.adapter;

import android.content.Context;
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
 * Created by sangz on 2016/6/7.
 */
public class UserRssAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<RssSource> rssSources;
    private Context context;

    public UserRssAdapter(Context context, List<RssSource> rssSources){
        this.context = context;
        this.rssSources = rssSources;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rss_list_item, parent, false);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).tvRssTitle.setText(rssSources.get(position).getRssName());
        }
    }

    @Override
    public int getItemCount() {
        return rssSources.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRssTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            tvRssTitle = (TextView) itemView.findViewById(R.id.user_rss_title);

        }
    }
}
