package me.shkschneider.skeleton.android.os

import android.os.Build
import android.telephony.TelephonyManager
import me.shkschneider.skeleton.android.helper.ScreenHelper
import me.shkschneider.skeleton.android.provider.SystemProperties
import me.shkschneider.skeleton.android.provider.SystemServices

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
        return is64bits && Build.SUPPORTED_64_BIT_ABIS.isNotEmpty()
    }

    val manufacturer: String
        get() = Build.MANUFACTURER

    val model: String
        get() = Build.MODEL

    // TODO read R.bool.sk_tablet?
    val tablet: Boolean
        get() = ScreenHelper.inches().toDouble() >= 7.0

    override fun toString(): String = "$brand/$manufacturer/$codename/$model"

}
