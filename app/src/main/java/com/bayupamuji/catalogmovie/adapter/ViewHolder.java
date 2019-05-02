package com.bayupamuji.catalogmovie.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bumptech.glide.Glide;

class ViewHolder {
    private final TextView tvName;
    private final TextView tvDesk;
    private final ImageView ivPhoto;

    ViewHolder(View view){
        tvName = view.findViewById(R.id.tv_name);
        tvDesk = view.findViewById(R.id.tv_desc);
        ivPhoto = view.findViewById(R.id.iv_movie);
    }

    void bind(DataMovie movie, Context context){
        String url = "http://image.tmdb.org/t/p/w185_and_h278_bestv2"+movie.getPoster_path();
        tvName.setText(movie.getTitle());
        tvDesk.setText(movie.getOverview());
        Glide.with(context).load(url).into(ivPhoto);
    }
}
