package com.org.news.network.event;

import com.org.news.model.Article;

import java.util.List;

/**
 * Created by babar on 5/17/2018.
 */

public class NewsEvent {

    public String message = "";
    public String status = "";
    public List<Article> articles = null;

    public NewsEvent(String message, String status, List<Article> articles) {
        this.message = message;
        this.status = status;
        this.articles = articles;
    }
}
