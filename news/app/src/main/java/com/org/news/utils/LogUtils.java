package com.org.news.utils;

import android.util.Log;


/**
 */
public final class LogUtils {

    private LogUtils() {
        // This utility class is not publicly instantiable
    }

    /**
     *
     * @param TAG
     * @param message
     */
    public static void  error(String TAG, String message){
        Log.e(TAG,message);
    }


    /**
     *
     * @param TAG
     * @param message
     */
    public static void  debug(String TAG, String message){
        Log.d(TAG,message);
    }


    /**
     *
     * @param TAG
     * @param message
     */
    public static void  info(String TAG, String message){
        Log.i(TAG,message);
    }

}
