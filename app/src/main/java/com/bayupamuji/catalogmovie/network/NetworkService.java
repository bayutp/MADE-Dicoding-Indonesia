package com.bayupamuji.catalogmovie.network;

import com.bayupamuji.catalogmovie.network.response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkService {
    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String api);
}
