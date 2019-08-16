package com.bayupamuji.catalogmovie.network.response;

import com.bayupamuji.catalogmovie.data.DataMovie;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private final List<DataMovie> results = new ArrayList<>();

    public List<DataMovie> getMovieList() {
        return results;
    }
}
