package me.shkschneider.skeleton;

import android.app.Application;
import android.content.Context;

import me.shkschneider.skeleton.helper.DeviceHelper;
import me.shkschneider.skeleton.helper.IdHelper;
import me.shkschneider.skeleton.helper.LogHelper;

/**
 * https://developer.android.com/reference/android/app/Application.html
 *
 *     onCreate()
 */
public class SkeletonApplication extends Application {

    public static Boolean DEBUG = false;
    public static Context CONTEXT = null;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();

        // LogHelper.v("Hello, world!");
        LogHelper.v("Device:" + DeviceHelper.codename());
        LogHelper.v("Id:" + IdHelper.androidId());
    }

}
