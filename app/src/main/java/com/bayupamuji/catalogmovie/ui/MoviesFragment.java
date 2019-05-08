package com.bayupamuji.catalogmovie.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bayupamuji.catalogmovie.BuildConfig;
import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.adapter.ItemClickListener;
import com.bayupamuji.catalogmovie.adapter.MoviesAdapter;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.data.DataTvShow;
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

public class MoviesFragment extends Fragment {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    private MoviesAdapter adapter;
    private final List<DataMovie> dataMovies = new ArrayList<>();
    private RestService restService;
    private RecyclerView listView;
    private ProgressBar progressBar;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRest();
        loadData();

        adapter = new MoviesAdapter(getActivity(), dataMovies, new ItemClickListener() {
            @Override
            public void onItemClick(DataMovie dataMovie) {
//                DataMovie data = new DataMovie(dataMovie.getTitle(), dataMovie.getPoster_path(), dataMovie.getOverview());
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(EXTRA_MOVIE, dataMovie.getId());
                startActivity(intent);
            }

            @Override
            public void onItemClick(DataTvShow dataTvShow) { }
        });
        listView = view.findViewById(R.id.rc_movies);
        progressBar = view.findViewById(R.id.progress_movies);
        listView.setHasFixedSize(true);

        setupList();
    }

    private void setupList() {
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(adapter);
    }

    private void loadData() {
        String api = BuildConfig.TMDB_API_KEY;
        restService.getMovies(api, new RestService.MovieCallback() {
            @Override
            public void onSuccess(MovieResponse response) {
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                adapter.updateMovie(response.getMovieList());
            }

            @Override
            public void onError(Throwable error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
