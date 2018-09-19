package me.shkschneider.skeleton.helper

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresPermission
import java.util.UUID

/**
 * > Hey, I lost the server password. What is it, again?
 * > It's... wait. How do I know it's really you?
 * > Ooh, good question! I bet we can construct a cool proof-of-identity protocol. I'll start by picking two random...
 * > Oh good; it's you. Here's the password...
 * <https://xkcd.com/1121/>
 */
// <https://developers.google.com/instance-id/>
object IdHelper {

    // <http://stackoverflow.com/q/22743087>
    @RequiresPermission("com.google.android.providers.gsf.permission.READ_GSERVICES")
    fun googleServiceFrameworkId(): String? {
        val cursor = ContextHelper.applicationContext().contentResolver.query(Uri.parse("content://com.google.android.gsf.gservices"), null, null, arrayOf("android_id"), null) ?: return null
        var gsfId: String? = null
        try {
            if (! cursor.moveToFirst() || cursor.columnCount < 2) {
                throw Exception()
            }
            gsfId = java.lang.Long.toHexString(java.lang.Long.parseLong(cursor.getString(1)))
        } catch (e: NumberFormatException) {
            Logger.wtf(e)
        } finally {
            cursor.close()
        }
        return gsfId
    }

    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    fun serial(): String {
        if (Build.VERSION.SDK_INT >= 26) {
            return Build.getSerial()
        } else {
            @Suppress("DEPRECATION")
            return androidId() ?: Build.UNKNOWN
        }
    }

    @SuppressLint("HardwareIds")
    @Deprecated("Using getString to get device identifiers is not recommended.")
    private fun androidId(): String? {
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
