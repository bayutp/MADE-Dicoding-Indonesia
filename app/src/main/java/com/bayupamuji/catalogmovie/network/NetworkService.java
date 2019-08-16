package com.bayupamuji.catalogmovie.network;

import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.data.DataTvShow;
import com.bayupamuji.catalogmovie.network.response.MovieResponse;
import com.bayupamuji.catalogmovie.network.response.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {
    @GET("movie/popular")
    Call<MovieResponse> getMovies(@Query("api_key") String api);

    @GET("tv/popular")
    Call<TvShowResponse> getTvShow(@Query("api_key") String api);

    @GET("movie/{movie_id}")
    Call<DataMovie> getMovieDetail(@Path("movie_id") String id, @Query("api_key") String api);

    @GET("tv/{tv_id}")
    Call<DataTvShow> getTvDetail(@Path("tv_id") String id, @Query("api_key") String api);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovieResult(@Query("api_key") String api, @Query("query") String query);

    @GET("search/tv")
    Call<TvShowResponse> getSearchTvResult(@Query("api_key") String api, @Query("query") String query);
}
