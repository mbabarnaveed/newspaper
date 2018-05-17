package com.org.news.sharestorage;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.org.news.model.Article;
import com.org.news.utils.LogUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class NewsPreference {

    private static final String TAG = NewsPreference.class.getCanonicalName();


    private static final String PREF_NEWS= "pref_news";

    private Preference mPreference = null;

    private static NewsPreference instance = null;

    /**
     * @param {@link Context}
     * @return NewsPreference {@link NewsPreference}
     */
    public static NewsPreference getInstance(final Context pContext) {

        if (instance == null) {
            instance = new NewsPreference(pContext);
        }
        return instance;
    }

    public NewsPreference(final Context pContext) {
        mPreference = new Preference(pContext);
    }


    /**
     *
     * @param articles
     */
    public void saveNews(final List<Article> articles){

        try{
            Gson gson = new Gson();
            String articlesJson = gson.toJson(articles);
            mPreference.saveStringPreference(PREF_NEWS,articlesJson);
        }catch (Exception ex){
            LogUtils.error(TAG, "Error while storing articles :- " + ex.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public List<Article> getNews(){

        List<Article> articles = null;

        try{
            Gson gson = new Gson();
            String articlesJson = mPreference.getStringPreference(PREF_NEWS,"");
            Type type = new TypeToken<List<Article>>() {}.getType();
            articles = gson.fromJson(articlesJson, type);

            if(articles==null)
                articles=  new ArrayList<Article>();

        }catch (Exception ex){
            LogUtils.error(TAG, "Error while getting articles :- " + ex.getMessage());
        }

        return articles ;

    }

}
