package me.shkschneider.skeleton

import android.app.Application
import android.content.pm.ApplicationInfo
import me.shkschneider.skeleton.extensions.has
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.DeviceHelper
import me.shkschneider.skeleton.helperx.Logger

/**
 * https://developer.android.com/reference/android/app/Application.html
 *
 * DEBUGGABLE
 * onCreate()
 */
abstract class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextHelper.applicationContext(applicationContext)
        DEBUGGABLE = applicationInfo.flags.has(ApplicationInfo.FLAG_DEBUGGABLE)
//        if (ApplicationHelper.debuggable()) {
//            ExceptionHelper.uncaughtException(object: ExceptionHelper.Callback {
//                override fun uncaughtException(throwable: Throwable) {
//                    throwable.printStackTrace()
//                }
//            })
//        }
        Logger.verbose("Hello, ${DeviceHelper.codename()} ($DeviceHelper)!")
    }

    companion object {

        var DEBUGGABLE: Boolean = false
            private set

    }

}
