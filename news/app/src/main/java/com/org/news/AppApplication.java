package com.org.news;

import android.app.Application;
import android.content.Context;

/**
 * Created by babar on 5/16/2018.
 */

public class AppApplication extends Application {

    public static Context appContext = null;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
