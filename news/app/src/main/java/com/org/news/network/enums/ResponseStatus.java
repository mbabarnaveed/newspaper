package com.org.news.network.enums;

/**
 * Created by babar on 5/17/2018.
 */

public enum ResponseStatus {

    OK ("ok"),
    FAIL ("fail"),
    ERROR ("error");

    private String url;

    ResponseStatus(String url) {
        this.url = url;
    }

    public String value() {
        return url;
    }
}
