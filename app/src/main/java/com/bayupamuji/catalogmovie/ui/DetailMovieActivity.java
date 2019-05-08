package com.bayupamuji.catalogmovie.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bayupamuji.catalogmovie.BuildConfig;
import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.network.NetworkService;
import com.bayupamuji.catalogmovie.network.response.RestService;
import com.bumptech.glide.Glide;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bayupamuji.catalogmovie.ui.MoviesFragment.EXTRA_MOVIE;

public class DetailMovieActivity extends AppCompatActivity {

    private ImageView ivPhoto;
    private TextView tvName,tvDesc;
    private RestService restService;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        initView();
        initRest();
        loadData();
    }

    private void setupToolbar(String title) {
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadData() {
        String api = BuildConfig.TMDB_API_KEY;
        String id = getIntent().getStringExtra(EXTRA_MOVIE);
        restService.getMovieDetail(api, id, new RestService.MovieDetailCallback() {
            @Override
            public void onSuccess(DataMovie response) {
                progressBar.setVisibility(View.GONE);
                ivPhoto.setVisibility(View.VISIBLE);
                tvName.setVisibility(View.VISIBLE);
                tvDesc.setVisibility(View.VISIBLE);

                String title = response.getTitle();
                String path = "http://image.tmdb.org/t/p/w185_and_h278_bestv2" + response.getPoster_path();
                String overview = response.getOverview();

                tvName.setText(title);
                tvDesc.setText(overview);
                Glide.with(DetailMovieActivity.this).load(path).into(ivPhoto);

                setupToolbar(title);
            }

            @Override
            public void onError(Throwable error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailMovieActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        ivPhoto = findViewById(R.id.iv_photo);
        tvName = findViewById(R.id.tv_name_detail);
        tvDesc = findViewById(R.id.tv_desc_detail);
        progressBar = findViewById(R.id.progress_movie_detail);

    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    private void initRest(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        restService = new RestService(networkService);
    }
}
