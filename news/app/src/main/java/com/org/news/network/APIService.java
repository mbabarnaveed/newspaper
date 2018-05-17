package com.org.news.network;

import com.org.news.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by babar on 5/17/2018.
 */

public interface APIService {

    @GET("v2/top-headlines")
    Call<NewsResponse> topHeadLines(@Query("country") String country,
                                    @Query("category") String category,
                                    @Query("apiKey") String apiKey);
}
