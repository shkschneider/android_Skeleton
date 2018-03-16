package me.shkschneider.skeleton.helper

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.support.annotation.Size
import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.java.SkHide
import me.shkschneider.skeleton.ui.BitmapHelper

object ApplicationHelper {

    val DEFAULT_ICON = android.R.drawable.sym_def_app_icon
    val INSTALLER = "com.android.vending"

    fun debuggable(): Boolean {
        // <https://stackoverflow.com/a/25517680/603270>
        return SkeletonApplication.DEBUGGABLE
    }

    fun resources(): Resources {
        return ContextHelper.applicationContext().resources
    }

    fun packageName(): String {
        return ContextHelper.applicationContext().packageName
    }

    fun packageManager(): PackageManager {
        return ContextHelper.applicationContext().packageManager
    }

    fun name(): String? {
        try {
            val packageManager = packageManager()
            val applicationInfo = packageManager.getApplicationInfo(packageName(), 0)
            val label = applicationInfo.loadLabel(packageManager)
            if (label == null) {
                LogHelper.warning("Label was NULL")
                return null
            }
            return label.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    // <http://semver.org>
    @Size(3)
    fun semanticVersions(): Array<Int>? {
        val versionName = versionName() ?: return null
        if (! versionName.matches("^\\d+\\.\\d+\\.\\d+$".toRegex())) {
            LogHelper.warning("Not semantic versioning")
            return null
        }
        val versionNames = versionName.split("\\.".toRegex())
        return arrayOf(versionNames[0].toInt(), versionNames[1].toInt(), versionNames[2].toInt())
    }

    fun versionName(): String? {
        try {
            val packageManager = packageManager()
            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun versionCode(): Int {
        try {
            val packageManager = packageManager()
            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            LogHelper.wtf(e)
            return -1
        }
    }

    fun flavor(): String? {
        TODO()
    }

    fun icon(): Bitmap? {
        try {
            val packageManager = packageManager()
            val applicationInfo = packageManager.getApplicationInfo(packageName(), 0)
            val drawable = applicationInfo.loadIcon(packageManager)
            return BitmapHelper.fromDrawable(drawable)
        } catch (e: PackageManager.NameNotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun permissions(): List<String>? {
        try {
            val packageManager = packageManager()
            val packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_PERMISSIONS)
            return packageInfo.requestedPermissions.toList()
        } catch (e: PackageManager.NameNotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    @SuppressLint("PackageManagerGetSignatures")
    fun signature(): String? {
        val packageManager = packageManager()
        try {
            val packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_SIGNATURES)
            val signatures = packageInfo.signatures
            return signatures[0].toString()
        } catch (e: PackageManager.NameNotFoundException) {
            LogHelper.wtf(e)
            return null
        }

    }

    fun installer(): String {
        return installer(packageName())
    }

    @SkHide
    fun installer(packageName: String): String {
        return packageManager().getInstallerPackageName(packageName)
    }

    fun installedFromGooglePlay(): Boolean {
        return installer().equals(INSTALLER, true)
    }

}
