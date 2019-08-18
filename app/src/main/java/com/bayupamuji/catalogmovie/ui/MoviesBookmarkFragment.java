package com.bayupamuji.catalogmovie.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.adapter.ItemClickListener;
import com.bayupamuji.catalogmovie.adapter.MoviesAdapter;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.data.DataTvShow;
import com.bayupamuji.catalogmovie.database.DatabaseHelper;

import java.util.Objects;

import static com.bayupamuji.catalogmovie.ui.MoviesFragment.EXTRA_MOVIE;

public class MoviesBookmarkFragment extends Fragment {


    private RecyclerView rcBookmark;
    private MoviesAdapter adapter;
    private ProgressBar progressBar;
    private Cursor cursor;

    public MoviesBookmarkFragment() {
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
        rcBookmark = view.findViewById(R.id.rc_movies);
        progressBar = view.findViewById(R.id.progress_movies);
        Context context = view.getContext();
        loadData();
        initView();
    }

    private void initView() {
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
        rcBookmark.setHasFixedSize(true);
        rcBookmark.setAdapter(adapter);
        rcBookmark.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void loadData() {
        progressBar.setVisibility(View.GONE);
        rcBookmark.setVisibility(View.VISIBLE);
        new LoadMovieAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor>{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getActivity()).getContentResolver().query(DatabaseHelper.CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            MoviesBookmarkFragment.this.cursor = cursor;
            adapter.updateMovie(cursor);
        }
    }
}
