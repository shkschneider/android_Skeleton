package me.shkschneider.skeleton.helper;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

public class BroadcastHelper {

    protected BroadcastHelper() {
        // Empty
    }

    public static void register(@NonNull final BroadcastReceiver broadcastReceiver, @NonNull final IntentFilter intentFilter) {
        LocalBroadcastManager.getInstance(ContextHelper.applicationContext())
                .registerReceiver(broadcastReceiver, intentFilter);
    }

    public static void send(@NonNull final Intent intent) {
        LocalBroadcastManager.getInstance(ContextHelper.applicationContext())
                .sendBroadcast(intent);
    }

    public static void unregister(final BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver == null) {
            return;
        }
        // <https://stackoverflow.com/a/3568906>
        try {
            LocalBroadcastManager.getInstance(ContextHelper.applicationContext())
                    .unregisterReceiver(broadcastReceiver);
        }
        catch (final IllegalArgumentException e) {
            LogHelper.wtf(e);
        }
    }

}
