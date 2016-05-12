package com.abitalo.www.rss_aggregator.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.model.RSSItem;

import java.util.List;

/**
 * Created by Zela on 2016/5/5.
 */
public class ListAdapter extends RecyclerView.Adapter<ListItem>{
private List<RSSItem> list;

public ListAdapter(List<RSSItem> list) {
        this.list = list;
        }

@Override
public ListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rss_item, parent, false);
        return new ListItem(view);
}

@Override
public void onBindViewHolder(ListItem holder, int position) {
        holder.description.setText(list.get(position).getDescription());
        holder.title.setText(list.get(position).getTitle());
        holder.date.setText(list.get(position).getPubdate());
        holder.square.setText(list.get(position).getTitle().charAt(0)+"");
        holder.url=list.get(position).getLink();
        }

@Override
public int getItemCount() {
        return list.size();
        }
}
