package me.shkschneider.skeleton.android.os

import android.Manifest
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.android.helper.ScreenHelper
import me.shkschneider.skeleton.android.provider.AndroidSystemProperties
import me.shkschneider.skeleton.android.provider.SystemServices

// <http://developer.android.com/reference/android/os/Build.html>
object DeviceHelper {

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Unreliable.")
    val isPhone: Boolean
        @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
        get() = SystemServices.telephonyManager()?.phoneType != TelephonyManager.PHONE_TYPE_NONE

    val architecture: String?
        get() = AndroidSystemProperties.get(AndroidSystemProperties.OS_ARCH)

    val brand: String
        get() = Build.BRAND

    val codename: String
        get() = Build.DEVICE

    // Hidden Build.IS_EMULATOR
    val emulator: Boolean
        get() = (AndroidSystemProperties.get("ro.kernel.qemu") == "1")

    val is64bits: Boolean
        get() = Build.SUPPORTED_64_BIT_ABIS.isNotEmpty()

    val manufacturer: String
        get() = Build.MANUFACTURER

    val model: String
        get() = Build.MODEL

    // TODO read R.bool.sk_tablet?
    val tablet: Boolean
        get() = ScreenHelper.inches().toDouble() >= 7.0

    override fun toString(): String = "$brand/$manufacturer/$codename/$model"

}
