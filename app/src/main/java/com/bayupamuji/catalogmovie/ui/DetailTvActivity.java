package com.bayupamuji.catalogmovie.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataTvShow;
import com.bumptech.glide.Glide;

import static com.bayupamuji.catalogmovie.ui.TvShowFragment.EXTRA_TV;

public class DetailTvActivity extends AppCompatActivity {

    private ImageView ivPhoto;
    private TextView tvName,tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        initView();
        loadData();
    }

    private void loadData() {
        DataTvShow data = getIntent().getParcelableExtra(EXTRA_TV);
        String name = data.getName();
        String path = "http://image.tmdb.org/t/p/w185_and_h278_bestv2" + data.getPoster_path();
        String overview = data.getOverview();

        tvName.setText(name);
        tvDesc.setText(overview);
        Glide.with(this).load(path).into(ivPhoto);

        setupToolbar(name);
    }

    private void setupToolbar(String title) {
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        ivPhoto = findViewById(R.id.iv_photo);
        tvName = findViewById(R.id.tv_name_detail);
        tvDesc = findViewById(R.id.tv_desc_detail);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }
}
