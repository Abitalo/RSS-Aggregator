package com.abitalo.www.rss_aggregator.util;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.listener.ItemOnClickListener;
import com.abitalo.www.rss_aggregator.model.RSSItem;

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
        holder.descriptionstr = list.get(position).getDescription();
        holder.title.setText(list.get(position).getTitle());
        holder.mAbstract.setText(list.get(position).getMabstract());
        if(null == list.get(position).getAuthor()){
            holder.author.setVisibility(View.GONE);
            holder.dot.setVisibility(View.GONE);
        }
        else {
            holder.dot.setVisibility(View.VISIBLE);
            holder.author.setVisibility(View.VISIBLE);
            holder.author.setText("by "+list.get(position).getAuthor());
        }
        holder.url = list.get(position).getLink();
        holder.authorstr = list.get(position).getAuthor();
        holder.encoded = list.get(position).getEncoded();

        Uri uri;
        String imgstr = list.get(position).getImageUrl();
        if (null != imgstr && imgstr.startsWith("http")) {
            holder.image.invalidate();
            holder.imgStr = imgstr;
            uri = Uri.parse(imgstr);
            holder.image.setImageURI(uri);
        } else {
            holder.image.invalidate();
            holder.image.setImageURI(null);
            holder.image.setVisibility(View.GONE);
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
                holder.date.setText(diffSeconds + " sec ago");
            } else if (0 == diffHours) {
                holder.date.setText(diffMinutes + " min ago");
            } else if (0 == diffDays) {
                holder.date.setText(diffHours + " hour ago");
            } else if (0 == diffMons) {
                holder.date.setText(diffDays + " day ago");
            } else {
                holder.date.setText(diffMons + " mon ago");
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
