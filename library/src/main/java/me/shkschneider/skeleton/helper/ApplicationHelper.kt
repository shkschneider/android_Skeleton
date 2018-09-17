package me.shkschneider.skeleton.helper

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Build
import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.ui.BitmapHelper

object ApplicationHelper {

    // ApplicationInfo.loadDefaultIcon(packageManager)
    const val DEFAULT_ICON = android.R.drawable.sym_def_app_icon
    const val INSTALLER = "com.android.vending"

    fun debuggable(): Boolean {
        // <https://stackoverflow.com/a/25517680/603270>
        return SkeletonApplication.DEBUGGABLE
    }

    fun resources(): Resources {
        // Resources.getSystem()
        return ContextHelper.applicationContext().resources
    }

    fun packageName(): String {
        return ContextHelper.applicationContext().packageName
    }

    fun packageManager(): PackageManager {
        return ContextHelper.applicationContext().packageManager
    }

    fun applicationInfo(packageName: String = ApplicationHelper.packageName()): ApplicationInfo? {
        try {
            val packageManager = packageManager()
            return packageManager.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

    fun name(packageName: String = ApplicationHelper.packageName()): String? {
        val label = applicationInfo(packageName)?.loadLabel(packageManager()) ?: run {
            Logger.warning("Label was NULL")
            return null
        }
        return label.toString()
    }

    fun exists(packageName: String = ApplicationHelper.packageName()): Boolean {
        try {
            return (packageManager().getApplicationInfo(packageName, 0) != null)
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            return false
        }
    }

    fun packageInfo(packageName: String = ApplicationHelper.packageName(), flags: Int = 0): PackageInfo? {
        try {
            val packageManager = packageManager()
            return packageManager.getPackageInfo(packageName, flags)
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

    fun versionName(packageName: String = ApplicationHelper.packageName()): String? {
        return packageInfo(packageName, PackageManager.GET_META_DATA)?.versionName ?: run {
            Logger.warning("VersionName was NULL")
            return null
        }
    }

    @SuppressLint("NewApi")
    fun versionCode(packageName: String = ApplicationHelper.packageName()): Long? {
        val packageInfo = packageInfo(packageName, PackageManager.GET_META_DATA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
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

    fun buildTime(packageName: String = ApplicationHelper.packageName()): Long? {
        return packageInfo(packageName, PackageManager.GET_META_DATA)?.lastUpdateTime ?: run {
            Logger.warning("LastUpdateTime was NULL")
            return null
        }
    }

    fun permissions(packageName: String = ApplicationHelper.packageName()): List<String>? {
        return packageInfo(packageName, PackageManager.GET_PERMISSIONS)?.requestedPermissions?.toList()
                ?: run {
                    Logger.warning("RequestedPermissions was NULL")
                    return null
                }
    }

    @Deprecated("You should use @DrawableRes when possible.")
    fun icon(packageName: String = ApplicationHelper.packageName()): Bitmap? {
        try {
            val packageManager = packageManager()
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            val drawable = applicationInfo.loadIcon(packageManager)
            return BitmapHelper.fromDrawable(drawable)
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

    @TargetApi(AndroidHelper.API_19)
    fun clear() {
        Logger.info("clearApplicationUserData()")
        SystemServices.activityManager()?.clearApplicationUserData()
    }

}
