package me.shkschneider.skeleton.helper

import android.support.annotation.IntRange
import android.util.Log
import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.extensions.ellipsize

object Logger {

    private const val VERBOSE = Log.VERBOSE
    private const val DEBUG = Log.DEBUG
    private const val INFO = Log.INFO
    private const val WARN = Log.WARN
    private const val ERROR = Log.ERROR
    private const val WTF = Log.ASSERT

    private fun log(@IntRange(from = VERBOSE.toLong(), to = WTF.toLong()) level: Int, msg: String?, throwable: Throwable?) {
        // <https://developer.android.com/reference/android/util/Log.html>
        val tag = SkeletonApplication.TAG.ellipsize(23, reverse = true)
        var prefix = ""
        if (ApplicationHelper.debuggable()) {
            Throwable().stackTrace.dropWhile { it.className == this.javaClass.name }.also { elements ->
                elements[0]?.let { element ->
                    prefix = "[${element.className.substringAfterLast(".")}.${element.methodName.substringBefore("$")}():${element.lineNumber}] "
                }
            }
        }
        when (level) {
            VERBOSE -> Log.v(tag, prefix + msg, throwable)
            DEBUG -> Log.d(tag, prefix + msg, throwable)
            INFO -> Log.i(tag, prefix + msg, throwable)
            WARN -> Log.w(tag, prefix + msg, throwable)
            ERROR -> Log.e(tag, prefix + msg, throwable)
            WTF -> Log.wtf(tag, prefix + msg, throwable)
        }
    }

    fun debug(msg: String, throwable: Throwable? = null) {
        log(DEBUG, msg, throwable)
    }

    fun verbose(msg: String, throwable: Throwable? = null) {
        log(VERBOSE, msg, throwable)
    }

    fun info(msg: String, throwable: Throwable? = null) {
        log(INFO, msg, throwable)
    }

    fun warning(msg: String, throwable: Throwable? = null) {
        log(WARN, msg, throwable)
    }

    fun error(msg: String, throwable: Throwable? = null) {
        log(ERROR, msg, throwable)
    }

    fun wtf(throwable: Throwable) {
        log(WTF, throwable.message, throwable)
    }

}
