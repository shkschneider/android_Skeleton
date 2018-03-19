package me.shkschneider.skeleton.helper

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.Settings
import android.support.annotation.RequiresPermission
import java.util.*

// <https://developers.google.com/instance-id/>
object IdHelper {

    // <http://stackoverflow.com/q/22743087>
    @SuppressLint("Recycle")
    @RequiresPermission("com.google.android.providers.gsf.permission.READ_GSERVICES")
    fun googleServiceFrameworkId(): String? {
        val cursor = ContextHelper.applicationContext().contentResolver.query(Uri.parse("content://com.google.android.gsf.gservices"), null, null, arrayOf("android_id"), null) ?: return null
        var gsfId: String? = null
        try {
            if (! cursor.moveToFirst() || cursor.columnCount < 2) {
                throw Exception()
            }
            gsfId = java.lang.Long.toHexString(java.lang.Long.parseLong(cursor.getString(1)))
        } catch (e: Exception) {
            Logger.wtf(e)
        } finally {
            cursor.close()
        }
        return gsfId
    }

    @SuppressLint("HardwareIds")
    @Deprecated("Using getString to get device identifiers is not recommended.")
    fun androidId(): String? {
        val androidId = Settings.Secure.getString(ContextHelper.applicationContext().contentResolver, Settings.Secure.ANDROID_ID)
        if (androidId.isNullOrBlank()) {
            Logger.warning("AndroidId was NULL")
            return null
        }
        return androidId.toLowerCase()
    }

    fun uuid(id: String): String {
        return UUID.nameUUIDFromBytes(id.toByteArray()).toString()
    }

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

}
