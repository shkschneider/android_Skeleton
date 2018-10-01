package me.shkschneider.skeleton.helper

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager

object BroadcastHelper {

    fun register(broadcastReceiver: BroadcastReceiver, vararg intentFilters: IntentFilter) {
        for (intentFilter in intentFilters) {
            LocalBroadcastManager.getInstance(ContextHelper.applicationContext())
                    .registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    fun send(intent: Intent) {
        LocalBroadcastManager.getInstance(ContextHelper.applicationContext())
                .sendBroadcast(intent)
    }

    fun unregister(broadcastReceiver: BroadcastReceiver) {
        // <https://stackoverflow.com/a/3568906>
        try {
            LocalBroadcastManager.getInstance(ContextHelper.applicationContext())
                    .unregisterReceiver(broadcastReceiver)
        } catch (e: IllegalArgumentException) {
            Logger.wtf(e)
        }
    }

}
