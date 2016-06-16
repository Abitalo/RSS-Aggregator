package com.abitalo.www.rss_aggregator.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.abitalo.www.rss_aggregator.view.RssSourceView;
import com.abitalo.www.rss_aggregator.view.SignInView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by sangzhenya on 2016/5/10.
 * 源分类列表适配器
 */
public class FacetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<Facet> facets;

    private static final int TYPE_LIST = 1;
    private static final int TYPE_WATERFALL = 2;
    private static final int TYPE_TITLE = 3;

    private Fragment fragment;

    public FacetAdapter(Context context, List<Facet> facets, Fragment fragment) {
        this.facets = facets;
        this.fragment = fragment;
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
            holder.itemView.setTag(facets.get(position).getId());
            ((ViewHolder) holder).ivFacetImage.setImageURI(Uri.parse(facets.get(position).getBackgroundUrl()));
        }
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
        showResource((Integer) v.getTag());
    }

    private void showResource(int facetId) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_discovery_main, new RssSourceView(facetId, RssSourceView.FACET), "rss_source").commit();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFacetName;
        public SimpleDraweeView ivFacetImage;
        public ViewHolder(View itemView) {
            super(itemView);
            tvFacetName = (TextView) itemView.findViewById(R.id.big_facet_card_title);
            ivFacetImage = (SimpleDraweeView) itemView.findViewById(R.id.big_facet_card_image);
        }
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextViewHolder(View itemView) {
            super(itemView);
        }
    }
}
