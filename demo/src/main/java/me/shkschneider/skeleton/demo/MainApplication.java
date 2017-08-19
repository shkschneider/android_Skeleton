package me.shkschneider.skeleton.demo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.ContextHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.ShortcutHelper;
import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.network.Proxy;

public class MainApplication extends SkeletonApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        LogHelper.verbose("DEBUGGABLE=" + ApplicationHelper.debuggable());

        Proxy.get().getRequestQueue().getCache().clear();

        shortcut("About");
    }

    private void shortcut(@NonNull final String shortcut) {
        ShortcutHelper.setDynamicShortcuts(new ShortcutHelper.Shortcut(StringHelper.lower(shortcut),
                R.mipmap.ic_launcher,
                StringHelper.capitalize(shortcut),
                new Intent(ContextHelper.applicationContext(), AboutActivity.class)
                        .setFlags(IntentHelper.FLAGS_CLEAR)));
    }

    @Override
    protected void attachBaseContext(final Context context) {
        super.attachBaseContext(context);

        MultiDex.install(context);
    }

}
