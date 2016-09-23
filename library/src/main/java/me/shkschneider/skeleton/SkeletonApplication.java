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
    // FIXME Do not place Android context classes in static fields; this is a memory leak (and also breaks Intant Run)
    public static Context CONTEXT = null;

    @Override
    public void onCreate() {
        super.onCreate();

        DEBUG = ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        CONTEXT = getApplicationContext();

        LogHelper.verbose("Hello, " + DeviceHelper.codename() + "!");
    }

}
