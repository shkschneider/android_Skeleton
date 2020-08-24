package me.shkschneider.skeleton.android.helper

import android.media.Ringtone
import android.media.RingtoneManager
import me.shkschneider.skeleton.android.provider.ContextProvider

object SoundHelper {

    fun notification(): Ringtone {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        return RingtoneManager.getRingtone(ContextProvider.applicationContext(), uri)
    }

    fun ringtone(): Ringtone {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        return RingtoneManager.getRingtone(ContextProvider.applicationContext(), uri)
    }

    fun alarm(): Ringtone {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        return RingtoneManager.getRingtone(ContextProvider.applicationContext(), uri)
    }

    @Deprecated("All types of sounds.")
    fun all(): Ringtone {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
        return RingtoneManager.getRingtone(ContextProvider.applicationContext(), uri)
    }

}
