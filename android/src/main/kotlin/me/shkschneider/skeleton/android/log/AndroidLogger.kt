package me.shkschneider.skeleton.android.log

import android.util.Log
import androidx.annotation.IntRange
import me.shkschneider.skeleton.android.core.helper.ApplicationHelper
import me.shkschneider.skeleton.android.core.helper.ContextHelper
import me.shkschneider.skeleton.kotlin.ellipsize
import me.shkschneider.skeleton.kotlin.log.ILogger

object AndroidLogger : ILogger {

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
        var fingerprint = if (ApplicationHelper.debuggable) Logger.fingerprint else ""
        msg.split("\n").forEach { line ->
            when (level) {
                VERBOSE -> Log.v(tag, fingerprint + line, throwable)
                DEBUG -> Log.d(tag, fingerprint + line, throwable)
                INFO -> Log.i(tag, fingerprint + line, throwable)
                WARN -> Log.w(tag, fingerprint + line, throwable)
                ERROR -> Log.e(tag, fingerprint + line, throwable)
                WTF -> Log.wtf(tag, fingerprint + line, throwable)
            }
        }
    }

    override fun debug(msg: String, throwable: Throwable?) =
            log(DEBUG, msg, throwable)

    override fun debug(msg: String) = debug(msg, null)

    override fun verbose(msg: String, throwable: Throwable?) =
            log(VERBOSE, msg, throwable)

    override fun verbose(msg: String) = verbose(msg, null)

    override fun info(msg: String, throwable: Throwable?) =
            log(INFO, msg, throwable)

    override fun info(msg: String) = info(msg, null)

    override fun warning(msg: String, throwable: Throwable?) =
            log(WARN, msg, throwable)

    override fun warning(msg: String) = warning(msg, null)

    override fun error(msg: String, throwable: Throwable?) =
            log(ERROR, msg, throwable)

    override fun error(msg: String) = error(msg, null)

    // Useful to log exceptions (avoids Exception.printStackTrace())
    override fun wtf(throwable: Throwable) =
            // "What a Terrible Failure"
            log(WTF, throwable::class.java.simpleName, throwable)

}
