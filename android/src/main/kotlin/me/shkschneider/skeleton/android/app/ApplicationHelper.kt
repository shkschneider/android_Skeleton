package me.shkschneider.skeleton.android.app

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.android.os.AndroidHelper
import me.shkschneider.skeleton.android.provider.SystemServices
import me.shkschneider.skeleton.android.provider.ContextProvider

object ApplicationHelper {

    // ApplicationInfo.loadDefaultIcon(packageManager)
    const val DEFAULT_ICON = android.R.drawable.sym_def_app_icon
    const val INSTALLER = "com.android.vending"

    // <https://stackoverflow.com/a/25517680/603270>
    val debuggable: Boolean
        get() = SkeletonApplication.DEBUGGABLE

    // Resources.getSystem()
    val resources: Resources
        get() = ContextProvider.applicationContext().resources

    val packageName: String
        get() = ContextProvider.applicationContext().packageName

    val packageManager: PackageManager
        get() = ContextProvider.applicationContext().packageManager

    fun applicationInfo(packageName: String = ApplicationHelper.packageName): ApplicationInfo? =
        try {
            packageManager.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            null
        }

    fun name(packageName: String = ApplicationHelper.packageName): String? =
        applicationInfo(packageName)?.loadLabel(packageManager)?.toString() ?: run {
            Logger.warning("Label was NULL")
            null
        }

    fun exists(packageName: String = ApplicationHelper.packageName): Boolean =
        try {
            packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            false
        }

    fun packageInfo(packageName: String = ApplicationHelper.packageName, flags: Int = 0): PackageInfo? =
        try {
            packageManager.getPackageInfo(packageName, flags)
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            null
        }

    fun versionName(packageName: String = ApplicationHelper.packageName): String? =
        packageInfo(packageName, PackageManager.GET_META_DATA)?.versionName ?: run {
            Logger.warning("VersionName was NULL")
            return null
        }

    @SuppressLint("NewApi")
    fun versionCode(packageName: String = ApplicationHelper.packageName): Long? {
        val packageInfo = packageInfo(packageName, PackageManager.GET_META_DATA)
        if (Build.VERSION.SDK_INT >= 28) {
            return packageInfo?.longVersionCode ?: run {
                Logger.warning("LongVersionCode was NULL")
                return null
            }
        } else {
            @Suppress("DEPRECATION")
            return packageInfo?.versionCode?.toLong() ?: run {
                Logger.warning("VersionCode was NULL")
                return null
            }
        }
    }

    fun buildTime(packageName: String = ApplicationHelper.packageName): Long? =
        packageInfo(packageName, PackageManager.GET_META_DATA)?.lastUpdateTime ?: run {
            Logger.warning("LastUpdateTime was NULL")
            return null
        }

    fun permissions(packageName: String = ApplicationHelper.packageName): List<String>? =
        packageInfo(packageName, PackageManager.GET_PERMISSIONS)?.requestedPermissions?.toList() ?: run {
            Logger.warning("RequestedPermissions was NULL")
            return null
        }

    fun icon(packageName: String = ApplicationHelper.packageName): Int? =
        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            applicationInfo.icon
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            null
        }

    fun drawable(packageName: String = ApplicationHelper.packageName) : Drawable? =
        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            applicationInfo.loadIcon(packageManager)
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            null
        }

    @TargetApi(AndroidHelper.API_19)
    fun clear() {
        Logger.info("clearApplicationUserData()")
        SystemServices.activityManager()?.clearApplicationUserData()
    }

}
