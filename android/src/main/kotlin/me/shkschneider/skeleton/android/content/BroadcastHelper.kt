package me.shkschneider.skeleton.android.content

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull

object BroadcastHelper {

    fun register(broadcastReceiver: BroadcastReceiver, vararg intentFilters: IntentFilter) {
        for (intentFilter in intentFilters) {
            LocalBroadcastManager.getInstance(ContextProvider.applicationContext()).registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    fun send(intent: Intent) {
        LocalBroadcastManager.getInstance(ContextProvider.applicationContext()).sendBroadcast(intent)
    }

    // <https://stackoverflow.com/a/3568906>
    fun unregister(broadcastReceiver: BroadcastReceiver) {
        tryOrNull {
            LocalBroadcastManager.getInstance(ContextProvider.applicationContext()).unregisterReceiver(broadcastReceiver)
        }
    }

}
