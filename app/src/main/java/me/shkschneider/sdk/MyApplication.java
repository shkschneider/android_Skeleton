package me.shkschneider.sdk;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();
    }

}
