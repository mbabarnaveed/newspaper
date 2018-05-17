package com.org.news.network.event;

import com.org.news.model.Article;

import java.util.List;

/**
 *
 */

public class NewsSearchEvent {

    public String string = "";

    public NewsSearchEvent(String string) {
        this.string = string;
    }
}
