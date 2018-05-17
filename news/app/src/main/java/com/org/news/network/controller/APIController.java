package com.org.news.network.controller;

import com.org.news.AppApplication;
import com.org.news.BuildConfig;
import com.org.news.R;
import com.org.news.model.NewsResponse;
import com.org.news.network.APIService;
import com.org.news.network.RestClient;
import com.org.news.network.enums.ResponseStatus;
import com.org.news.network.event.NewsEvent;
import com.org.news.sharestorage.NewsPreference;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by babar on 5/17/2018.
 */

public class APIController {

    private static String TAG = APIController.class.getCanonicalName();

    private static APIController instance = new APIController();
    private EventBus eventBus = EventBus.getDefault();

    public APIService apiService;
    public static APIController getInstance() {
        if(instance == null)
            instance = new APIController();
        return instance;
    }

    public APIController(){
        apiService = RestClient.getApiService();
    }

    /**
     *
     * @param country
     * @param category
     */
    public void topHeadLines(String country,String category){

        String key = BuildConfig.API_KEY;

        Call<NewsResponse> call =apiService.topHeadLines(country,category,key);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if(response.isSuccessful() && response.body()!=null){

                    NewsResponse newsResponse = response.body();

                    //Store news in share storage
                    if(newsResponse.articles!=null)
                       NewsPreference.getInstance(AppApplication.appContext)
                               .saveNews(newsResponse.articles);

                    eventBus.post(new NewsEvent(response.message(),
                            ResponseStatus.OK.value(),newsResponse.articles));
                }else{
                    eventBus.post(new NewsEvent(response.message(),
                            ResponseStatus.FAIL.value(),null));
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                eventBus.post(new NewsEvent(checkInterNetIssue(t),
                        ResponseStatus.ERROR.value(),null));
            }
        });

    }

    /**
     *
     * @param throwable
     * @return
     */
    public String  checkInterNetIssue(final Throwable throwable){
        String msg = "";

        if(throwable instanceof IOException){
            msg = AppApplication.appContext.getString(R.string.no_internet_connection);
        }else{
            msg = throwable.getMessage();
        }
        return  msg;
    }
}
