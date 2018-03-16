package me.shkschneider.skeleton.helper

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresPermission
import android.support.v4.content.ContextCompat

// <http://developer.android.com/reference/android/os/Build.html>
object DeviceHelper {

    fun tablet(): Boolean {
        val screenSize = ScreenHelper.inches().toDouble()
        return screenSize >= 7.0
    }

    fun brand(): String {
        return Build.BRAND
    }

    fun manufacturer(): String {
        return Build.MANUFACTURER
    }

    fun model(): String {
        return Build.MODEL
    }

    fun codename(): String {
        return Build.DEVICE
    }

    fun architecture(): String? {
        return SystemProperties.get(SystemProperties.OS_ARCH)
    }

    @SuppressLint("NewApi")
    fun is64bits(): Boolean {
        val is64bits: Boolean = AndroidHelper.api() >= AndroidHelper.ANDROID_5
        return if (AndroidHelper.api() >= AndroidHelper.API_21) {
            is64bits && Build.SUPPORTED_64_BIT_ABIS.isNotEmpty()
        } else is64bits
    }

    @Suppress("DEPRECATION")
    @SuppressLint("NewApi", "HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    fun serial(): String? {
        if (AndroidHelper.api() >= AndroidHelper.API_26
                && ContextCompat.checkSelfPermission(ContextHelper.applicationContext(),
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return Build.getSerial()
        }
        return Build.SERIAL
    }

}
