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
    TextView mAbstract = null;
    TextView author = null;
    TextView dot = null;

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getmAbstract() {
        return mAbstract;
    }

    public void setmAbstract(TextView mAbstract) {
        this.mAbstract = mAbstract;
    }

    public TextView getAuthor() {
        return author;
    }

    public void setAuthor(TextView author) {
        this.author = author;
    }

    public TextView getDot() {
        return dot;
    }

    public void setDot(TextView dot) {
        this.dot = dot;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    public String getDescriptionstr() {
        return descriptionstr;
    }

    public void setDescriptionstr(String descriptionstr) {
        this.descriptionstr = descriptionstr;
    }

    public String getAuthorstr() {
        return authorstr;
    }

    public void setAuthorstr(String authorstr) {
        this.authorstr = authorstr;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public SimpleDraweeView getImage() {
        return image;
    }

    public void setImage(SimpleDraweeView image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    SimpleDraweeView image = null;
    String url = null;
    String descriptionstr = null;
    String encoded = null;
    String authorstr = null;
    String imgStr = null;
    ItemOnClickListener mListener;

    public ListItem(View itemView,ItemOnClickListener listener){
        super(itemView);
//        square=(TextView)itemView.findViewById(R.id.square);
        title = (TextView) itemView.findViewById(R.id.item_title);
        date = (TextView) itemView.findViewById(R.id.item_date);
        author = (TextView) itemView.findViewById(R.id.item_author);
        dot = (TextView) itemView.findViewById(R.id.rss_dot);
        mAbstract = (TextView) itemView.findViewById(R.id.item_abstract);
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
        item.put("author",authorstr);
        if(null != mListener){
            mListener.onItemClick(v,item);
        }
    }
}
