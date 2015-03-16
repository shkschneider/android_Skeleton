package me.shkschneider.skeleton.demo;

import me.shkschneider.skeleton.SkeletonApplication;

public class MainApplication extends SkeletonApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        DEBUG = BuildConfig.DEBUG;
        CONTEXT = getApplicationContext();
    }

}
