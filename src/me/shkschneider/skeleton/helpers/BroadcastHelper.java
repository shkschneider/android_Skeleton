package me.shkschneider.skeleton.helpers;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import me.shkschneider.skeleton.SkeletonApplication;

@SuppressWarnings("unused")
public final class BroadcastHelper {

    public static boolean register(final BroadcastReceiver broadcastReceiver, final IntentFilter intentFilter) {
        if (broadcastReceiver == null) {
            LogHelper.warning("BroadcastReceiver was NULL");
            return false;
        }
        if (intentFilter == null) {
            LogHelper.warning("IntentFilter was NULL");
            return false;
        }

        SkeletonApplication.CONTEXT.registerReceiver(broadcastReceiver, intentFilter);
        return true;
    }

    public static boolean unregister(final BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver == null) {
            LogHelper.warning("BroadcastReceiver was NULL");
            return false;
        }

        SkeletonApplication.CONTEXT.unregisterReceiver(broadcastReceiver);
        return true;
    }

    public static boolean send(final Intent intent) {
        if (intent == null) {
            LogHelper.warning("Intent was NULL");
            return false;
        }

        SkeletonApplication.CONTEXT.sendBroadcast(intent);
        return true;
    }

}
