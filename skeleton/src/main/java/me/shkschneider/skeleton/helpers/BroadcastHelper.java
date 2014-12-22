package me.shkschneider.skeleton.helpers;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.SkeletonApplication;

public class BroadcastHelper {

    public static boolean register(@NonNull final BroadcastReceiver broadcastReceiver, @NonNull final IntentFilter intentFilter) {
        SkeletonApplication.CONTEXT.registerReceiver(broadcastReceiver, intentFilter);
        return true;
    }

    public static boolean unregister(@NonNull final BroadcastReceiver broadcastReceiver) {
        SkeletonApplication.CONTEXT.unregisterReceiver(broadcastReceiver);
        return true;
    }

    public static boolean send(@NonNull final Intent intent) {
        SkeletonApplication.CONTEXT.sendBroadcast(intent);
        return true;
    }

}
