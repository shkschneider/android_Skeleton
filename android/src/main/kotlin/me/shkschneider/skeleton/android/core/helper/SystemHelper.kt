package me.shkschneider.skeleton.android.core.helper

import android.os.SystemClock
import me.shkschneider.skeleton.android.core.helperx.SystemProperties

// <http://developer.android.com/reference/android/content/Context.html>
object SystemHelper {

    fun uname(): String {
        return String.format(LocaleHelper.Device.locale(),
                "%s %s %s",
                System.getProperty(SystemProperties.OS_NAME),
                System.getProperty(SystemProperties.OS_VERSION),
                System.getProperty(SystemProperties.OS_ARCH))
    }

    fun sinceBoot(): Long {
        // Returns milliseconds since boot, including time spent in sleep.
        return SystemClock.elapsedRealtime()
    }

    fun uptime(): Long {
        // Returns milliseconds since boot, not counting time spent in deep sleep.
        return SystemClock.uptimeMillis()
    }

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Does not return until at least the specified number of milliseconds has elapsed.") // Avoid
    fun sleep(milliseconds: Long) {
        SystemClock.sleep(milliseconds)
    }

}
