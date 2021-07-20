package com.colman.pawnitv2;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;


public class MyApplication extends Application {
    public static Context context;
    public final static Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        com.colman.pawnitv2.MyApplication.context = getApplicationContext();
    }

}
