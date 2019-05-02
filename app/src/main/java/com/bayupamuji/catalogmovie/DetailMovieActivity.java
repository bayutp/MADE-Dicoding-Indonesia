package com.bayupamuji.catalogmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bumptech.glide.Glide;

public class DetailMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        DataMovie data = getIntent().getParcelableExtra(MainActivity.EXTRA_MOVIE);

        String title = data.getTitle();
        String path = "http://image.tmdb.org/t/p/w185_and_h278_bestv2"+data.getPoster_path();
        String overview = data.getOverview();

        ImageView ivPhoto = findViewById(R.id.iv_photo);
        TextView tvName = findViewById(R.id.tv_name_detail);
        TextView tvDesc = findViewById(R.id.tv_desc_detail);

        tvName.setText(title);
        tvDesc.setText(overview);
        Glide.with(this).load(path).into(ivPhoto);
    }
}
