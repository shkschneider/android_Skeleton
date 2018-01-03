package me.shkschneider.skeleton.helper

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.Settings
import android.support.annotation.RequiresPermission
import android.text.TextUtils

import java.util.UUID

import me.shkschneider.skeleton.java.StringHelper

// <https://developers.google.com/instance-id/>
object IdHelper {

    // <http://stackoverflow.com/q/22743087>
    @SuppressLint("Recycle")
    @RequiresPermission("com.google.android.providers.gsf.permission.READ_GSERVICES")
    fun googleServiceFrameworkId(): String? {
        val cursor = ContextHelper.applicationContext().contentResolver.query(Uri.parse("content://com.google.android.gsf.gservices"), null, null, arrayOf("android_id"), null) ?: return null
        var gsfId: String? = null
        try {
            if (!cursor.moveToFirst() || cursor.columnCount < 2) {
                throw Exception()
            }
            gsfId = java.lang.Long.toHexString(java.lang.Long.parseLong(cursor.getString(1)))
        } catch (e: Exception) {
            LogHelper.wtf(e)
        } finally {
            cursor.close()
        }
        return gsfId
    }

    @SuppressLint("HardwareIds")
    @Deprecated("Using getString to get device identifiers is not recommended.")
    fun androidId(): String? {
        val androidId = Settings.Secure.getString(ContextHelper.applicationContext().contentResolver, Settings.Secure.ANDROID_ID)
        if (TextUtils.isEmpty(androidId)) {
            LogHelper.warning("AndroidId was NULL")
            return null
        }
        return StringHelper.lower(androidId)
    }

    fun uuid(id: String): String {
        return UUID.nameUUIDFromBytes(id.toByteArray()).toString()
    }

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

}
