package me.shkschneider.skeleton.helperx

import android.util.Log
import androidx.annotation.IntRange
import me.shkschneider.skeleton.extensions.ellipsize
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper

object Logger {

    private const val VERBOSE = Log.VERBOSE
    private const val DEBUG = Log.DEBUG
    private const val INFO = Log.INFO
    private const val WARN = Log.WARN
    private const val ERROR = Log.ERROR
    private const val WTF = Log.ASSERT

    private fun log(@IntRange(from = VERBOSE.toLong(), to = WTF.toLong()) level: Int, msg: String, throwable: Throwable?) {
        // <https://developer.android.com/reference/android/util/Log.html>
        // <https://developer.android.com/studio/debug/am-logcat>
        val tag = ContextHelper.applicationContext().packageName.ellipsize(23, reverse = true)
        var prefix = ""
        if (ApplicationHelper.debuggable) {
            Throwable().stackTrace.dropWhile { it.className == this.javaClass.name }.also { elements ->
                elements[0]?.let { element ->
                    val parent = element.className.substringAfterLast(".").substringBefore("$")
                    val child = element.methodName.substringBefore("$").takeIf { it != "invoke" } ?: "$"
                    prefix = "[$parent.$child():${element.lineNumber}] "
                }
            }
        }
        msg.split("\n").forEach { line ->
            when (level) {
                VERBOSE -> Log.v(tag, prefix + line, throwable)
                DEBUG -> Log.d(tag, prefix + line, throwable)
                INFO -> Log.i(tag, prefix + line, throwable)
                WARN -> Log.w(tag, prefix + line, throwable)
                ERROR -> Log.e(tag, prefix + line, throwable)
                WTF -> Log.wtf(tag, prefix + line, throwable)
            }
        }
    }

    // Debug logs are compiled in but stripped at runtime.
    fun debug(msg: String, throwable: Throwable? = null): Logger {
        log(DEBUG, msg, throwable)
        return this
    }

    // You should never compile verbose logs into your app, except during development.
    fun verbose(msg: String, throwable: Throwable? = null): Logger {
        log(VERBOSE, msg, throwable)
        return this
    }

    fun info(msg: String, throwable: Throwable? = null): Logger {
        log(INFO, msg, throwable)
        return this
    }

    fun warning(msg: String, throwable: Throwable? = null): Logger {
        log(WARN, msg, throwable)
        return this
    }

    fun error(msg: String, throwable: Throwable? = null): Logger {
        log(ERROR, msg, throwable)
        return this
    }

    // Useful to log exceptions (avoids Exception.printStackTrace())
    fun wtf(throwable: Throwable): Logger { // "What a Terrible Failure"
        log(WTF, throwable::class.java.simpleName, throwable)
        return this
    }

}
