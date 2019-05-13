package com.bayupamuji.catalogmovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayupamuji.catalogmovie.BuildConfig;
import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.data.DataTvShow;
import com.bumptech.glide.Glide;

class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvName;
    private final TextView tvDesk;
    private final ImageView ivPhoto;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
        tvDesk = itemView.findViewById(R.id.tv_desc);
        ivPhoto = itemView.findViewById(R.id.iv_movie);
    }

    void bind(DataMovie movie, Context context){
        String url = BuildConfig.BASE_IMAGE_URL+movie.getPoster_path();
        tvName.setText(movie.getTitle());
        tvDesk.setText(movie.getOverview());
        Glide.with(context).load(url).into(ivPhoto);
    }

    void bindTv(DataTvShow data, Context context){
        String url = BuildConfig.BASE_IMAGE_URL+data.getPoster_path();
        tvName.setText(data.getName());
        tvDesk.setText(data.getOverview());
        Glide.with(context).load(url).into(ivPhoto);
    }
}
