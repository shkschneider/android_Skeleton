package me.shkschneider.skeleton

import android.app.Application
import android.content.pm.ApplicationInfo
import me.shkschneider.skeleton.extensions.has
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.DeviceHelper
import me.shkschneider.skeleton.helperx.log.Logger

/**
 * https://developer.android.com/reference/android/app/Application.html
 * https://developer.android.com/studio/build/multidex
 *
 * DEBUGGABLE
 * onCreate()
 *
 * If minSdkVersion is 21+ you do not need MultiDex (nor multiDexEnabled true).
 */
abstract class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextHelper.applicationContext(applicationContext)
        DEBUGGABLE = applicationInfo.flags.has(ApplicationInfo.FLAG_DEBUGGABLE)
//        if (ApplicationHelper.debuggable()) {
//            ExceptionHelper.uncaughtException {
//                it.printStackTrace()
//            }
//        }
        Logger.verbose("Hello, ${DeviceHelper.codename} ($DeviceHelper)!")
    }

    companion object {

        var DEBUGGABLE: Boolean = false
            private set

    }

}
