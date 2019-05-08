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
import com.bayupamuji.catalogmovie.adapter.TvAdapter;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.data.DataTvShow;
import com.bayupamuji.catalogmovie.network.NetworkService;
import com.bayupamuji.catalogmovie.network.response.RestService;
import com.bayupamuji.catalogmovie.network.response.TvShowResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TvShowFragment extends Fragment {

    public static final String EXTRA_TV = "EXTRA_TV";

    private TvAdapter adapter;
    private final List<DataTvShow> dataTvShows = new ArrayList<>();
    private RestService restService;
    private RecyclerView listView;
    private ProgressBar progressBar;

    public TvShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRest();
        loadData();

        adapter = new TvAdapter(getActivity(), dataTvShows, new ItemClickListener() {
            @Override
            public void onItemClick(DataMovie dataMovie) {

            }

            @Override
            public void onItemClick(DataTvShow dataTvShow) {
                Intent intent = new Intent(getActivity(), DetailTvActivity.class);
                intent.putExtra(EXTRA_TV, dataTvShow.getId());
                startActivity(intent);
            }
        });
        listView = view.findViewById(R.id.rc_tv);
        progressBar = view.findViewById(R.id.progress_tv);
        listView.setHasFixedSize(true);

        setupList();
    }

    private void setupList() {
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(adapter);
    }

    private void loadData() {
        String api = BuildConfig.TMDB_API_KEY;
        restService.getTvShow(api, new RestService.TvShowCallback() {
            @Override
            public void onSuccess(TvShowResponse response) {
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                adapter.updateTv(response.getResults());
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

