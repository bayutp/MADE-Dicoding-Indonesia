package com.bayupamuji.catalogmovie.adapter;

import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.data.DataTvShow;

public interface ItemClickListener {
    void onItemClick(DataMovie dataMovie);
    void onItemClick(DataTvShow dataTvShow);
}