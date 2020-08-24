package me.shkschneider.skeleton.android.core.helper

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.android.log.Logger
import java.util.Locale
import java.util.UUID

private const val GOOGLE_SERVICE_FRAMEWORK_URI = "content://com.google.android.gsf.gservices"
private const val ANDROID_ID = "android_id"

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
    @SuppressLint("Recycle")
    @RequiresPermission("com.google.android.providers.gsf.permission.READ_GSERVICES")
    @Deprecated("Use InstanceID", ReplaceWith("FirebaseInstanceId.getInstance().id"))
    fun googleServiceFrameworkId(): String? {
        val cursor = ContextHelper.applicationContext().contentResolver.query(Uri.parse(GOOGLE_SERVICE_FRAMEWORK_URI), null, null, arrayOf(ANDROID_ID), null) ?: return null
        var gsfId: String? = null
        try {
            if (!cursor.moveToFirst() || cursor.columnCount < 2) {
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

    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    @SuppressLint("MissingPermission")
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
        try {
            val androidId = Settings.Secure.getString(ContextHelper.applicationContext().contentResolver, Settings.Secure.ANDROID_ID)
            if (androidId.isNullOrBlank()) {
                Logger.warning("AndroidId was NULL")
                return null
            }
            return androidId.toLowerCase(Locale.getDefault())
        } catch (e: SecurityException) {
            Logger.wtf(e)
            return null
        }
    }

    fun uuid(id: String): String =
        UUID.nameUUIDFromBytes(id.toByteArray()).toString()

    fun randomUuid(): String =
        UUID.randomUUID().toString()

}
