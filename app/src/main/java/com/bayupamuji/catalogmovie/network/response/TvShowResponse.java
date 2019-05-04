package com.bayupamuji.catalogmovie.network.response;

import com.bayupamuji.catalogmovie.data.DataTvShow;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvShowResponse {
    @SerializedName("results")
    private final List<DataTvShow> results = new ArrayList<>();

    public List<DataTvShow> getResults() {
        return results;
    }
}
