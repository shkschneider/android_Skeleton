package me.shkschneider.skeleton.demo;

import android.content.Context;
import android.support.multidex.MultiDex;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helper.LogHelper;

public class MainApplication extends SkeletonApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        SkeletonApplication.DEBUG = BuildConfig.DEBUG;
        SkeletonApplication.CONTEXT = getApplicationContext();

        LogHelper.verbose("Hello, world!");
    }

    @Override
    protected void attachBaseContext(final Context context) {
        super.attachBaseContext(context);
        MultiDex.install(context);
    }

}
