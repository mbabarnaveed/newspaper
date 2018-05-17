package com.org.news.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 *
 */
public final class Loaders {

    public static ProgressDialog pDialog;

    // declare the dialog as a member field of your activity

    private Loaders() {
        // This utility class is not publicly instantiable
    }

    public static void showProgressDialog(Context context, String msg) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        pDialog.show();
    }





    public static void cancelProgressDialog() {
        if (pDialog != null)
            pDialog.cancel();
    }

    public static void setMessage(final String msg) {
        if (pDialog != null)
            pDialog.setMessage(msg);
    }


}
