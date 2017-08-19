package me.shkschneider.skeleton.demo;

import android.content.Context;
import android.support.multidex.MultiDex;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.network.Proxy;

public class MainApplication extends SkeletonApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        LogHelper.verbose("DEBUGGABLE=" + SkeletonApplication.DEBUGGABLE);

        Proxy.get().getRequestQueue().getCache().clear();
    }

    @Override
    protected void attachBaseContext(final Context context) {
        super.attachBaseContext(context);

        MultiDex.install(context);
    }

}
