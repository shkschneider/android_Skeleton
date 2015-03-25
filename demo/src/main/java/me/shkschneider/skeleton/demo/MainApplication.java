package me.shkschneider.skeleton.demo;

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

}
