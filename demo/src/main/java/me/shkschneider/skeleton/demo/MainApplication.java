package me.shkschneider.skeleton.demo;

import android.content.Context;

import me.shkschneider.skeleton.SkeletonApplication;

public class MainApplication extends SkeletonApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        SkeletonApplication.DEBUG = BuildConfig.DEBUG;
    }

    @Override
    protected void attachBaseContext(final Context context) {
        super.attachBaseContext(context);
        // MultiDex.install(context);
    }

}
