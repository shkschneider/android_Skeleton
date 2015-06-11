package me.shkschneider.skeleton;

import android.app.Application;
import android.content.Context;

import me.shkschneider.skeleton.helper.Log;

public class SkeletonApplication extends Application {

    public static Boolean DEBUG = false;
    public static Context CONTEXT = null;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();

        Log.v("Hello, world!");
    }

}
