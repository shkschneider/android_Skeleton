package me.shkschneider.skeleton;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import me.shkschneider.skeleton.helper.ContextHelper;
import me.shkschneider.skeleton.helper.DeviceHelper;
import me.shkschneider.skeleton.helper.LogHelper;

/**
 * https://developer.android.com/reference/android/app/Application.html
 *
 * DEBUG
 * TAG
 * onCreate()
 */
public abstract class SkeletonApplication extends Application {

    public static Boolean DEBUG = false;
    public static String TAG = BuildConfig.APPLICATION_ID;

    @Override
    public void onCreate() {
        super.onCreate();

        //noinspection deprecation
        ContextHelper.applicationContext(getApplicationContext());
        final ApplicationInfo applicationInfo = getApplicationInfo();
        DEBUG = ((applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        TAG = applicationInfo.packageName;

        LogHelper.verbose("Hello, " + DeviceHelper.codename() + "!");
    }

    @Deprecated
    public static Context getContext() {
        return ContextHelper.applicationContext();
    }

}
