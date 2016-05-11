package com.abitalo.www.rss_aggregator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abitalo.www.rss_aggregator.R;
import com.abitalo.www.rss_aggregator.model.Facet;

import java.util.List;

/**
 * Created by sangzhenya on 2016/5/10.
 * recycler适配器
 */
public class FacetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Facet> facets;

    private static final int TYPE_LIST = 1;
    private static final int TYPE_WATERFALL = 2;
    private static final int TYPE_TITLE = 3;

    private int selectedPos = 0;

    public FacetAdapter(Context context, List<Facet> facets) {
        this.context = context;
        this.facets = facets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_LIST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facet_card_big, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
            viewHolder = new ViewHolder(view);
            view.setOnClickListener(this);
        } else if (viewType == TYPE_WATERFALL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facet_card_small, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(false);
            view.setLayoutParams(layoutParams);
            viewHolder = new ViewHolder(view);
            view.setOnClickListener(this);
        } else if (viewType == TYPE_TITLE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facet_card_title, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
            return new TextViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).tvFacetName.setText(facets.get(position).getFacetName());
//            holder.itemView.setSelected(selectedPos == position);
            holder.itemView.setTag(position);
            //// TODO:需要添加图片的加载解析 2016/5/10
        } else if (holder instanceof TextViewHolder) {

        }
    }

    private void showResource(int position) {
        //// TODO:跳转到具体源的页面 2016/5/10
        Log.i("Discovery", facets.get(position).getFacetName());
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        } else if (position > 0 && position <= 6) {
            return TYPE_LIST;
        } else {
            return TYPE_WATERFALL;
        }
    }

    @Override
    public int getItemCount() {
        return facets.size();
    }

    @Override
    public void onClick(View v) {
        Log.i("NavDiscover", v.getTag() + "");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFacetName;
        public ImageView ivFacetImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvFacetName = (TextView) itemView.findViewById(R.id.big_facet_card_title);
//            ivFacetImage = (ImageView) itemView.findViewById(R.id.big_facet_card_image);
        }
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        public TextViewHolder(View itemView) {
            super(itemView);
        }
    }
}
