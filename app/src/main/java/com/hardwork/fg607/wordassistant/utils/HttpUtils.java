package com.hardwork.fg607.wordassistant.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hardwork.fg607.wordassistant.MyApplication;

/**
 * Created by fg607 on 15-11-24.
 */
public class HttpUtils {

    public static boolean isNetworkConnected () {

        Context context = MyApplication.getApplication();

        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }
}
