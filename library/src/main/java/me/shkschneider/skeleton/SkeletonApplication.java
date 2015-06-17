package me.shkschneider.skeleton;

import android.app.Application;
import android.content.Context;

import me.shkschneider.skeleton.helper.DeviceHelper;
import me.shkschneider.skeleton.helper.IdHelper;
import me.shkschneider.skeleton.helper.Log;

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

        // Log.v("Hello, world!");
        Log.v("Device:" + DeviceHelper.codename());
        Log.v("Id:" + IdHelper.androidId());
    }

}
