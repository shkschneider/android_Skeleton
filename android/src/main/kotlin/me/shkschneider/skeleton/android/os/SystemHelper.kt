package me.shkschneider.skeleton.android.os

import android.os.SystemClock
import me.shkschneider.skeleton.kotlin.os.JvmSystemProperty

// <http://developer.android.com/reference/android/content/Context.html>
object SystemHelper {

    fun uname(): String =
        String.format(LocaleHelper.Device.locale(),
            "%s %s %s",
            JvmSystemProperty.OsName.get(),
            JvmSystemProperty.OsVersion.get(),
            JvmSystemProperty.OsArch.get()
        )

    // Returns milliseconds since boot, including time spent in sleep.
    fun sinceBoot(): Long =
        SystemClock.elapsedRealtime()

    // Returns milliseconds since boot, not counting time spent in deep sleep.
    fun uptime(): Long = SystemClock.uptimeMillis()

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Does not return until at least the specified number of milliseconds has elapsed.") // Avoid
    fun sleep(milliseconds: Long) {
        SystemClock.sleep(milliseconds)
    }

}
