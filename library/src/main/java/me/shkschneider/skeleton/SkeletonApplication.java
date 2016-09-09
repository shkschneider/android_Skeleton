package me.shkschneider.skeleton;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import me.shkschneider.skeleton.helper.DeviceHelper;
import me.shkschneider.skeleton.helper.LogHelper;

/**
 * https://developer.android.com/reference/android/app/Application.html
 *
 *     onCreate()
 */
public abstract class SkeletonApplication extends Application {

    public static Boolean DEBUG = false;
    public static Context CONTEXT = null;

    @Override
    public void onCreate() {
        super.onCreate();

        DEBUG = ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        CONTEXT = getApplicationContext();

        LogHelper.verbose("Hello, " + DeviceHelper.codename() + "!");
    }

}
