package me.shkschneider.skeleton.helpers;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import me.shkschneider.skeleton.SkeletonApplication;

import org.jetbrains.annotations.NotNull;

public class BroadcastHelper {

    public static boolean register(@NotNull final BroadcastReceiver broadcastReceiver, @NotNull final IntentFilter intentFilter) {
        SkeletonApplication.CONTEXT.registerReceiver(broadcastReceiver, intentFilter);
        return true;
    }

    public static boolean unregister(@NotNull final BroadcastReceiver broadcastReceiver) {
        SkeletonApplication.CONTEXT.unregisterReceiver(broadcastReceiver);
        return true;
    }

    public static boolean send(@NotNull final Intent intent) {
        SkeletonApplication.CONTEXT.sendBroadcast(intent);
        return true;
    }

}
