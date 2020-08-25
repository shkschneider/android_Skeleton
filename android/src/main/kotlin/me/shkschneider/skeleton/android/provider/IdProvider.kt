package me.shkschneider.skeleton.android.provider

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
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
// <https://developer.android.com/training/articles/user-data-ids>
// <https://developers.google.com/instance-id/>
object IdHelper {

    // <http://stackoverflow.com/q/22743087>
    @SuppressLint("Recycle")
    @RequiresPermission("com.google.android.providers.gsf.permission.READ_GSERVICES")
    @Deprecated("Use InstanceID", ReplaceWith("FirebaseInstanceId.getInstance().id"))
    fun googleServiceFrameworkId(): String? {
        val cursor = ContextProvider.applicationContext().contentResolver.query(Uri.parse(GOOGLE_SERVICE_FRAMEWORK_URI), null, null, arrayOf(ANDROID_ID), null) ?: return null
        var gsfId: String? = null
        tryOrNull({
            if (!cursor.moveToFirst() || cursor.columnCount < 2) {
                throw Exception()
            }
            gsfId = java.lang.Long.toHexString(java.lang.Long.parseLong(cursor.getString(1)))
        }, finally = { cursor.close() })
        return gsfId
    }

    @Suppress("DEPRECATION")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    @SuppressLint("MissingPermission")
    fun serial(): String =
        if (Build.VERSION.SDK_INT >= 26) Build.getSerial() else androidId() ?: Build.UNKNOWN

    @Suppress("DeprecatedCallableAddReplaceWith")
    @SuppressLint("HardwareIds")
    @Deprecated("Using getString to get device identifiers is not recommended.")
    private fun androidId(): String? =
        tryOrNull {
            Settings.Secure.getString(ContextProvider.applicationContext().contentResolver, Settings.Secure.ANDROID_ID).toLowerCase(Locale.getDefault())
        }

    fun uuid(id: String): String =
        UUID.nameUUIDFromBytes(id.toByteArray()).toString()

    fun randomUuid(): String =
        UUID.randomUUID().toString()

}
