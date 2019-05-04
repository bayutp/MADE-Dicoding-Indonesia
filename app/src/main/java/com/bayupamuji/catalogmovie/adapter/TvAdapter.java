package com.bayupamuji.catalogmovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataTvShow;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<DataTvShow> dataTvShows;
    private final ItemClickListener itemClickListener;

    public TvAdapter(Context context, List<DataTvShow> dataTvShows, ItemClickListener itemClickListener) {
        this.context = context;
        this.dataTvShows = dataTvShows;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final DataTvShow data = dataTvShows.get(i);
        viewHolder.bindTv(data,context);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataTvShows.size();
    }

    public void updateTv(List<DataTvShow> dataTvShows){
        this.dataTvShows.clear();
        this.dataTvShows.addAll(dataTvShows);
        notifyDataSetChanged();
    }

}
