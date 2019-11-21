package com.example.sandboxa;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {
    public static Context app;
    public static StringBuffer vm = new StringBuffer();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }


}
