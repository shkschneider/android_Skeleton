package me.shkschneider.skeleton.android.core.helper

import android.media.Ringtone
import android.media.RingtoneManager
import me.shkschneider.skeleton.android.core.helper.ContextHelper

object SoundHelper {

    fun notification(): Ringtone {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        return RingtoneManager.getRingtone(ContextHelper.applicationContext(), uri)
    }

    fun ringtone(): Ringtone {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        return RingtoneManager.getRingtone(ContextHelper.applicationContext(), uri)
    }

    fun alarm(): Ringtone {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        return RingtoneManager.getRingtone(ContextHelper.applicationContext(), uri)
    }

    @Deprecated("All types of sounds.")
    fun all(): Ringtone {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
        return RingtoneManager.getRingtone(ContextHelper.applicationContext(), uri)
    }

}
