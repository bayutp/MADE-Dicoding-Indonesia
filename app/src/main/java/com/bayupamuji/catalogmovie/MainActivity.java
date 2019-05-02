package com.bayupamuji.catalogmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bayupamuji.catalogmovie.adapter.MoviesAdapter;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.network.NetworkService;
import com.bayupamuji.catalogmovie.network.response.MovieResponse;
import com.bayupamuji.catalogmovie.network.response.RestService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    private MoviesAdapter adapter;
    private final List<DataMovie> dataMovies = new ArrayList<>();
    private RestService restService;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRest();
        loadData();
        adapter = new MoviesAdapter(this,dataMovies);
        listView = findViewById(R.id.lv_list);
        listView.setAdapter(adapter);

    }

    private void loadData() {
        String api = "bd6d19dfe2e6c6eaed345e2f8bb52d19";
        restService.getMovies(api, new RestService.MyCallback() {
            @Override
            public void onSuccess(MovieResponse response) {
                dataMovies.clear();
                dataMovies.addAll(response.getMovieList());
                adapter.notifyDataSetChanged();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DataMovie data = new DataMovie(dataMovies.get(position).getTitle(),dataMovies.get(position).getPoster_path(),dataMovies.get(position).getOverview());
                        Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
                        intent.putExtra(EXTRA_MOVIE, data);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRest() {
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
