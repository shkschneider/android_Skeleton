package me.shkschneider.skeleton.helper

import android.support.annotation.IntRange
import android.util.Log

import me.shkschneider.skeleton.BuildConfig
import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.java.ObjectHelper
import me.shkschneider.skeleton.java.SkHide

object LogHelper {

    private const val VERBOSE = Log.VERBOSE
    private const val DEBUG = Log.DEBUG
    private const val INFO = Log.INFO
    private const val WARN = Log.WARN
    private const val ERROR = Log.ERROR
    private const val WTF = Log.ASSERT

    @SkHide
    private fun tag(): String {
        var tag = SkeletonApplication.TAG
        if (tag.isEmpty()) {
            tag = BuildConfig.APPLICATION_ID
        }
        while (tag.length > 23 && tag.contains(".")) {
            tag = tag.substring(tag.indexOf(".") + 1, tag.length)
        }
        if (tag.length > 23) {
            tag = tag.substring(tag.length - 23, tag.length)
        }
        return tag
    }

    private fun log(@IntRange(from = VERBOSE.toLong(), to = WTF.toLong()) level: Int, msg: String?, throwable: Throwable?) {
        val elements = Throwable().stackTrace
        var callerClassName = "?"
        var callerMethodName = "?"
        var callerLineNumber = "?"
        if (elements.size >= 3) {
            callerClassName = elements[2].className
            callerClassName = callerClassName.substring(callerClassName.lastIndexOf('.') + 1)
            if (callerClassName.indexOf("$") > 0) {
                callerClassName = callerClassName.substring(0, callerClassName.indexOf("$"))
            }
            callerMethodName = elements[2].methodName
            callerMethodName = callerMethodName.substring(callerMethodName.lastIndexOf('_') + 1)
            if (callerMethodName == "<init>") {
                callerMethodName = callerClassName
            }
            callerLineNumber = elements[2].lineNumber.toString()
        }
        val stack = "[" + callerClassName + "." + callerMethodName + "():" + callerLineNumber + "]" + if (msg.isNullOrBlank()) "" else " "
        when (level) {
            VERBOSE -> Log.v(tag(), stack + msg, throwable)
            DEBUG -> Log.d(tag(), stack + msg, throwable)
            INFO -> Log.i(tag(), stack + msg, throwable)
            WARN -> Log.w(tag(), stack + msg, throwable)
            ERROR -> Log.e(tag(), stack + msg, throwable)
            WTF -> Log.wtf(tag(), stack + msg, throwable)
        // else -> {}
        }
    }

    fun debug(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    fun debug(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    fun debug(msg: String) {
        log(DEBUG, msg, null)
    }

    fun debug(msg: String, throwable: Throwable) {
        log(DEBUG, msg, throwable)
    }

    fun verbose(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    fun verbose(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    fun verbose(msg: String) {
        log(VERBOSE, msg, null)
    }

    fun verbose(msg: String, throwable: Throwable) {
        log(VERBOSE, msg, throwable)
    }

    fun info(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    fun info(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    fun info(msg: String) {
        log(INFO, msg, null)
    }

    fun info(msg: String, throwable: Throwable) {
        log(INFO, msg, throwable)
    }

    fun warning(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    fun warning(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    fun warning(msg: String) {
        log(WARN, msg, null)
    }

    fun warning(msg: String, throwable: Throwable) {
        log(WARN, msg, throwable)
    }

    fun error(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    fun error(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    fun error(msg: String) {
        log(ERROR, msg, null)
    }

    fun error(msg: String, throwable: Throwable) {
        log(ERROR, msg, throwable)
    }

    fun wtf(throwable: Throwable) { // FIXME
        log(WTF, throwable.message, throwable)
    }

}
