package me.shkschneider.skeleton.helper

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Build
import android.support.annotation.RequiresPermission
import android.util.DisplayMetrics

// <http://developer.android.com/reference/android/os/Build.html>
object DeviceHelper {

    // <https://github.com/eyeem/deviceinfo>
    @SuppressLint("NewApi")
    fun screenSize(): Float {
        val windowManager = SystemServices.windowManager()
        val displayMetrics = DisplayMetrics()
        val display = windowManager?.defaultDisplay
        display?.getMetrics(displayMetrics)
        val point = Point(1, 1)
        if (AndroidHelper.api() >= AndroidHelper.API_17) {
            display?.getRealSize(point)
        } else {
            display?.getSize(point)
        }
        val w = point.x / displayMetrics.xdpi
        val h = point.y / displayMetrics.ydpi
        val screenSize = Math.sqrt(Math.pow(w.toDouble(), 2.0) + Math.pow(h.toDouble(), 2.0)).toFloat()
        LogHelper.verbose("screenSize: " + screenSize + "\"")
        return screenSize
    }

    fun tablet(): Boolean {
        val screenSize = screenSize().toDouble()
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
        return if (AndroidHelper.api() >= AndroidHelper.API_26) {
            Build.getSerial()
        } else Build.SERIAL
    }

}
