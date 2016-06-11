package com.abitalo.www.rss_aggregator.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.listener.ItemOnClickListener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

/**
 * Created by Zela on 2016/5/5.
 */
public class ListItem extends RecyclerView.ViewHolder implements View.OnClickListener  {
    TextView title = null;
    TextView date = null;
<<<<<<< HEAD
=======
    TextView mAbstract = null;
    TextView author = null;
    TextView dot = null;
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
    SimpleDraweeView image = null;
    String url = null;
    String descriptionstr = null;
    String encoded = null;
<<<<<<< HEAD
    String author = null;
=======
    String authorstr = null;
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
    String imgStr = null;
    ItemOnClickListener mListener;

    public ListItem(View itemView,ItemOnClickListener listener){
        super(itemView);
//        square=(TextView)itemView.findViewById(R.id.square);
        title = (TextView) itemView.findViewById(R.id.item_title);
        date = (TextView) itemView.findViewById(R.id.item_date);
<<<<<<< HEAD
=======
        author = (TextView) itemView.findViewById(R.id.item_author);
        dot = (TextView) itemView.findViewById(R.id.rss_dot);
        mAbstract = (TextView) itemView.findViewById(R.id.item_abstract);
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
        image = (SimpleDraweeView) itemView.findViewById(R.id.item_pic);
        mListener = listener;
        itemView.findViewById(R.id.item_card).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HashMap<String,String> item = new HashMap<>();
        item.put("title",title.getText().toString());
        item.put("date",date.getText().toString());
        item.put("description",descriptionstr);
        item.put("encoded",encoded);
        item.put("url",url);
<<<<<<< HEAD
        item.put("author",author);
=======
        item.put("author",authorstr);
>>>>>>> 16c1923c7129d3491bee375f1dd5e926e2507d9d
        if(null != mListener){
            mListener.onItemClick(v,item);
        }
    }
}
