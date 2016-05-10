package com.abitalo.www.rss_aggregator.adapter;

import android.content.Context;
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

import java.util.List;

/**
 * Created by sangzhenya on 2016/5/10.
 * recycler适配器
 */
public class FacetAdapter extends RecyclerView.Adapter<FacetAdapter.ViewHolder> {
    private Context context;
    private List<Facet> facets;
    private int line;
    public FacetAdapter(Context context, List<Facet> facets,int line) {
        this.context = context;
        this.facets = facets;
        this.line = line;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.big_facet_card, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams();
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
        }else if (viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_facet_card, parent, false);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams();
            layoutParams.setFullSpan(false);
            view.setLayoutParams(layoutParams);
        }


  /*      final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getPosition();
                showResource(position);
            }
        });*/
        return new ViewHolder(view);
    }

    private void showResource(int position) {
        //// TODO:跳转到具体源的页面 2016/5/10
        Log.i("Discovery",facets.get(position).getFacetName());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvFacetName.setText(facets.get(position).getFacetName());


        //// TODO:需要添加图片的加载解析 2016/5/10
    }

    @Override
    public int getItemViewType(int position) {
        if(position < 6){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return facets.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvFacetName;
        public ImageView ivFacetImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvFacetName = (TextView) itemView.findViewById(R.id.big_facet_card_title);
//            ivFacetImage = (ImageView) itemView.findViewById(R.id.big_facet_card_image);

        }
    }
}
