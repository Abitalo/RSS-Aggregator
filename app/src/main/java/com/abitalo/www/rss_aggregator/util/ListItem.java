package com.abitalo.www.rss_aggregator.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.R;

/**
 * Created by Zela on 2016/5/5.
 */
public class ListItem extends RecyclerView.ViewHolder  {
    TextView square = null;
    TextView title = null;
    TextView description = null;
    TextView date = null;
    String url = null;

    public ListItem(View itemView){
        super(itemView);
        square=(TextView)itemView.findViewById(R.id.square);
        title = (TextView) itemView.findViewById(R.id.item_title);
        description = (TextView) itemView.findViewById(R.id.item_des);
        date = (TextView) itemView.findViewById(R.id.item_date);
    }
}
