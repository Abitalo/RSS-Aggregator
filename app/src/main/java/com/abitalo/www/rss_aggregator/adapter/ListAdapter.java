package com.abitalo.www.rss_aggregator.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.listener.ItemOnClickListener;
import com.abitalo.www.rss_aggregator.model.RSSItem;
import com.abitalo.www.rss_aggregator.util.ListItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zela on 2016/5/5.
 */
public class ListAdapter extends RecyclerView.Adapter<ListItem> {
    private List<RSSItem> list;
    private ItemOnClickListener listener;
    private Date pubDate = null;
    private Date currentDate = null;
    private final DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    public ListAdapter(List<RSSItem> list) {
        this.list = list;
    }

    @Override
    public ListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rss_item, parent, false);
        return new ListItem(view, listener);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ListItem holder, int position) {
        position = getItemViewType(position);
        holder.setDescriptionstr(list.get(position).getDescription());
        holder.getTitle().setText(list.get(position).getTitle());
        holder.getmAbstract().setText(list.get(position).getMabstract());
        if(null == list.get(position).getAuthor()){
            holder.getAuthor().setVisibility(View.GONE);
            holder.getDot().setVisibility(View.GONE);
        }
        else {
            holder.getDot().setVisibility(View.VISIBLE);
            holder.getAuthor().setVisibility(View.VISIBLE);
            holder.getAuthor().setText("by "+list.get(position).getAuthor());
        }
        holder.setUrl(list.get(position).getLink());
        holder.setAuthorstr(list.get(position).getAuthor());
        holder.setEncoded(list.get(position).getEncoded());

        Uri uri;
        String imgstr = list.get(position).getImageUrl();
        if (null != imgstr && imgstr.startsWith("http")) {
            holder.getImage().invalidate();
            holder.setImgStr(imgstr);
            uri = Uri.parse(imgstr);
            holder.getImage().setImageURI(uri);
        } else {
            holder.getImage().invalidate();
            holder.getImage().setImageURI(null);
            holder.getImage().setVisibility(View.GONE);
        }

        try {
            pubDate = formatter.parse(list.get(position).getPubdate());
            currentDate = Calendar.getInstance().getTime();
            long diff = currentDate.getTime() - pubDate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;

            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            long diffMons = diffDays / 30;
            if (0 == diffMinutes) {
                holder.getDate().setText(diffSeconds + " sec ago");
            } else if (0 == diffHours) {
                holder.getDate().setText(diffMinutes + " min ago");
            } else if (0 == diffDays) {
                holder.getDate().setText(diffHours + " hour ago");
            } else if (0 == diffMons) {
                holder.getDate().setText(diffDays + " day ago");
            } else {
                holder.getDate().setText(diffMons + " mon ago");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ItemOnClickListener getListener() {
        return listener;
    }

    public void setListener(ItemOnClickListener listener) {
        this.listener = listener;
    }
}
