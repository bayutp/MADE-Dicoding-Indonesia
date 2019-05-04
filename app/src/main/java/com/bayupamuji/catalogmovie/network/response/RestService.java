package com.bayupamuji.catalogmovie.network.response;

import com.bayupamuji.catalogmovie.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestService {
    private final NetworkService networkService;

    public RestService(NetworkService networkService) {
        this.networkService = networkService;
    }

    public void getMovies(final String api, final MovieCallback callback){
        networkService.getMovies(api).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getTvShow(final String api, final TvShowCallback callback){
        networkService.getTvShow(api).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface MovieCallback {
        void onSuccess(MovieResponse response);
        void onError(Throwable error);
    }

    public interface TvShowCallback{
        void onSuccess(TvShowResponse response);
        void onError(Throwable error);
    }
}
