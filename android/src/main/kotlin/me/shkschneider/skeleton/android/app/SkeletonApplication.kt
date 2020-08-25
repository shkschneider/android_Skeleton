package me.shkschneider.skeleton.android.app

import android.app.Application
import android.content.pm.ApplicationInfo
import me.shkschneider.skeleton.kotlin.jvm.has
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.android.os.DeviceHelper
import me.shkschneider.skeleton.kotlin.jvm.ExceptionHelper
import me.shkschneider.skeleton.kotlin.log.Logger

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
        ContextProvider.applicationContext(applicationContext)
        DEBUGGABLE = applicationInfo.flags.has(ApplicationInfo.FLAG_DEBUGGABLE)
        if (ApplicationHelper.debuggable) {
            ExceptionHelper.uncaughtException {
                it.printStackTrace()
            }
        }
        Logger.verbose("Hello, ${DeviceHelper.codename} ($DeviceHelper)!")
    }

    companion object {

        var DEBUGGABLE: Boolean = false
            private set

    }

}
