package com.bayupamuji.catalogmovie.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bayupamuji.catalogmovie.BuildConfig;
import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.database.DatabaseHelper;
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
    private Boolean status = false;
    private Menu menu = null;
    private DatabaseHelper db;
    private String id,title,path_local,overview;

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
        id = getIntent().getStringExtra(EXTRA_MOVIE);
        restService.getMovieDetail(api, id, new RestService.MovieDetailCallback() {
            @Override
            public void onSuccess(DataMovie response) {
                progressBar.setVisibility(View.GONE);
                ivPhoto.setVisibility(View.VISIBLE);
                tvName.setVisibility(View.VISIBLE);
                tvDesc.setVisibility(View.VISIBLE);

                title = response.getTitle();
                String path = "http://image.tmdb.org/t/p/w185_and_h278_bestv2" + response.getPoster_path();
                overview = response.getOverview();
                path_local = response.getPoster_path();

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

        db = new DatabaseHelper(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        this.menu = menu;
        DataMovie data = db.getMovie(id);
        cekStatus(data);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_bookmark){
            if (status) {
                status = false;
                removeBookmark();
            } else {
                status = true;
                addBookmark();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void removeBookmark() {
        try{
            db.deleteMovie(id);
            changeIcon();
            Toast.makeText(this,title + getString(R.string.not_bookmark_msg),
                    Toast.LENGTH_SHORT).show();
        }catch (Throwable error){
            Toast.makeText(this,"Remove Failed: "+error.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void addBookmark() {
        try{
            db.insertMovies(id,title,path_local,overview);
            changeIcon();
            Toast.makeText(this,title+getString(R.string.bookmark_msg),
                    Toast.LENGTH_SHORT).show();
        }catch (Throwable error){
            Toast.makeText(this,"Bookmark Failed : "+error.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void changeIcon() {
        if (status)
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_bookmark));
        else
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_bookmark_border));
    }

    private void cekStatus(DataMovie data){
        if (null != data.getId()){
            status = true;
            changeIcon();
        }else{
            status = false;
            changeIcon();
        }
    }
}
