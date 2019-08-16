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

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.adapter.ItemClickListener;
import com.bayupamuji.catalogmovie.adapter.TvAdapter;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.data.DataTvShow;
import com.bayupamuji.catalogmovie.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.bayupamuji.catalogmovie.ui.TvShowFragment.EXTRA_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavoriteFragment extends Fragment {

    private RecyclerView rcBookmark;
    private List<DataTvShow> data = new ArrayList<>();
    private TvAdapter adapter;
    private ProgressBar progressBar;

    public TvFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcBookmark = view.findViewById(R.id.rc_tv);
        progressBar = view.findViewById(R.id.progress_tv);

        initView();
        loadData();
    }

    private void initView() {
        adapter = new TvAdapter(getActivity(), data, new ItemClickListener() {
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
        rcBookmark.setHasFixedSize(true);
        rcBookmark.setAdapter(adapter);
        rcBookmark.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void loadData() {
        DatabaseHelper db = new DatabaseHelper(getActivity());
        data = db.getAllTvShow();
        progressBar.setVisibility(View.GONE);
        rcBookmark.setVisibility(View.VISIBLE);
        adapter.updateTv(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
