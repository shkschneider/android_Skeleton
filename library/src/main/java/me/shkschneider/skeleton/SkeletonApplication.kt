package me.shkschneider.skeleton

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo

import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.DeviceHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.java.ExceptionHelper

/**
 * https://developer.android.com/reference/android/app/Application.html
 *
 * DEBUGGABLE
 * TAG
 * onCreate()
 */
abstract class SkeletonApplication : Application() {

    val context: Context get() = ContextHelper.applicationContext()

    override fun onCreate() {
        super.onCreate()

        @Suppress("DEPRECATION")
        ContextHelper.applicationContext(applicationContext)
        val applicationInfo = applicationInfo
        DEBUGGABLE = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        TAG = applicationInfo.packageName

        if (ApplicationHelper.debuggable()) {
            ExceptionHelper.uncaughtException { throwable -> throwable.printStackTrace() }
        }

        LogHelper.verbose("Hello, " + DeviceHelper.codename() + "!")
    }

    companion object {

        var DEBUGGABLE: Boolean? = false
        var TAG = BuildConfig.APPLICATION_ID

    }

}
