package me.shkschneider.skeleton.helper

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import me.shkschneider.skeleton.helper.DeviceHelper.brand
import me.shkschneider.skeleton.helper.DeviceHelper.manufacturer
import me.shkschneider.skeleton.helper.DeviceHelper.model
import me.shkschneider.skeleton.helperx.SystemProperties
import me.shkschneider.skeleton.helperx.SystemServices

// <http://developer.android.com/reference/android/os/Build.html>
object DeviceHelper {

    // TODO test
    val isPhone: Boolean
        get() = SystemServices.telephonyManager()?.phoneType != TelephonyManager.PHONE_TYPE_NONE

    val architecture: String?
        get() = SystemProperties.get(SystemProperties.OS_ARCH)

    val brand: String
        get() = Build.BRAND

    val codename: String
        get() = Build.DEVICE

    // Hidden Build.IS_EMULATOR
    val emulator: Boolean
        get() = (SystemProperties.get("ro.kernel.qemu") == "1")

    fun is64bits(): Boolean {
        val is64bits: Boolean = AndroidHelper.api() >= AndroidHelper.ANDROID_5
        if (Build.VERSION.SDK_INT >= 21) {
            return is64bits && Build.SUPPORTED_64_BIT_ABIS.isNotEmpty()
        }
        return is64bits
    }

    val manufacturer: String
        get() = Build.MANUFACTURER

    val model: String
        get() = Build.MODEL

    @Suppress("DEPRECATION")
    @SuppressLint( "HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    fun serial(): String? {
        if (Build.VERSION.SDK_INT >= 26
                && ContextCompat.checkSelfPermission(ContextHelper.applicationContext(),
                        Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return Build.getSerial()
        }
        return Build.SERIAL
    }

    // TODO read R.bool.sk_tablet?
    val tablet: Boolean
        get() = ScreenHelper.inches().toDouble() >= 7.0

    @SuppressLint("MissingPermission")
    override fun toString(): String {
        return "${brand}/${manufacturer}/${codename}/${model} '${serial()}'"
    }

}
