package me.shkschneider.skeleton

import android.app.Application
import android.content.pm.ApplicationInfo
import me.shkschneider.skeleton.extensions.has
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.DeviceHelper
import me.shkschneider.skeleton.helper.Logger

/**
 * https://developer.android.com/reference/android/app/Application.html
 *
 * DEBUGGABLE
 * TAG
 * onCreate()
 */
abstract class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextHelper.applicationContext(applicationContext)
        DEBUGGABLE = applicationInfo.flags.has(ApplicationInfo.FLAG_DEBUGGABLE)
        TAG = packageName
//        if (ApplicationHelper.debuggable()) {
//            ExceptionHelper.uncaughtException(object: ExceptionHelper.Callback {
//                override fun uncaughtException(throwable: Throwable) {
//                    throwable.printStackTrace()
//                }
//            })
//        }
        Logger.verbose("Hello, ${DeviceHelper.codename()}!")
    }

    companion object {

        var DEBUGGABLE: Boolean = false
        var TAG = BuildConfig.APPLICATION_ID

    }

}
