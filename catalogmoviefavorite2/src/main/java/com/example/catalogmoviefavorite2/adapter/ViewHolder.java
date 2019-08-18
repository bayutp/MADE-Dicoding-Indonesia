package com.example.catalogmoviefavorite2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.catalogmoviefavorite2.R;
import com.example.catalogmoviefavorite2.data.DataMovie;

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
        String url = context.getString(R.string.base_image_path)+movie.getPoster_path();
        tvName.setText(movie.getTitle());
        tvDesk.setText(movie.getOverview());
        Glide.with(context).load(url).into(ivPhoto);
    }
}
