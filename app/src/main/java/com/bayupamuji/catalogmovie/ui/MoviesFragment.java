package com.bayupamuji.catalogmovie.ui;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesFragment extends Fragment {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    private MoviesAdapter adapter;
    private RestService restService;
    private RecyclerView listView, rcSearch;
    private ProgressBar progressBar;
    private SearchView searchView;
    private String api = BuildConfig.TMDB_API_KEY;
    private SearchView.OnQueryTextListener queryTextListener;

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
        setHasOptionsMenu(true);
        adapter = new MoviesAdapter(getActivity(), new ItemClickListener() {
            @Override
            public void onItemClick(DataMovie dataMovie) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(EXTRA_MOVIE, dataMovie.getId());
                startActivity(intent);
            }

            @Override
            public void onItemClick(DataTvShow dataTvShow) {
            }
        });
        listView = view.findViewById(R.id.rc_movies);
        rcSearch = view.findViewById(R.id.rc_search_movies);
        progressBar = view.findViewById(R.id.progress_movies);
        listView.setHasFixedSize(true);
        rcSearch.setHasFixedSize(true);

        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcSearch.setLayoutManager(new LinearLayoutManager(getActivity()));

        listView.setAdapter(adapter);
    }

    private void loadData() {
        restService.getMovies(api, new RestService.MovieCallback() {
            @Override
            public void onSuccess(MovieResponse response) {
                progressBar.setVisibility(View.GONE);
                rcSearch.setVisibility(View.GONE);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (null != menuItem) {
            searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();
        }
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    if (query.isEmpty()){
                        return true;
                    }else {

                        String queries = query.toLowerCase().replace(" ", "%20");
                        rcSearch.setAdapter(adapter);
                        getSearchResult(queries);
                        return true;
                    }
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        if (menuItem != null) {
            menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    loadData();
                    return true;
                }
            });
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getSearchResult(String query) {
        restService.getSearchMovieResult(api, query, new RestService.MovieCallback() {
            @Override
            public void onSuccess(MovieResponse response) {
                rcSearch.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                adapter.updateMovie(response.getMovieList());
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(getActivity(), "error : " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            return false;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}
