package me.shkschneider.skeleton.helper

import android.support.annotation.IntRange
import android.text.TextUtils
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
        if (TextUtils.isEmpty(tag)) {
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
        val stack = "[" + callerClassName + "." + callerMethodName + "():" + callerLineNumber + "]" + if (TextUtils.isEmpty(msg)) "" else " "
        when (level) {
            VERBOSE -> Log.v(tag(), stack + msg!!, throwable)
            DEBUG -> Log.d(tag(), stack + msg!!, throwable)
            INFO -> Log.i(tag(), stack + msg!!, throwable)
            WARN -> Log.w(tag(), stack + msg!!, throwable)
            ERROR -> Log.e(tag(), stack + msg!!, throwable)
            WTF -> Log.wtf(tag(), stack + msg!!, throwable)
        // else -> {}
        }
    }

    @JvmStatic
    fun debug(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    @JvmStatic
    fun debug(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    @JvmStatic
    fun debug(msg: String) {
        log(DEBUG, msg, null)
    }

    @JvmStatic
    fun debug(msg: String, throwable: Throwable) {
        log(DEBUG, msg, throwable)
    }

    @JvmStatic
    fun verbose(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    @JvmStatic
    fun verbose(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    @JvmStatic
    fun verbose(msg: String) {
        log(VERBOSE, msg, null)
    }

    @JvmStatic
    fun verbose(msg: String, throwable: Throwable) {
        log(VERBOSE, msg, throwable)
    }

    @JvmStatic
    fun info(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    @JvmStatic
    fun info(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    @JvmStatic
    fun info(msg: String) {
        log(INFO, msg, null)
    }

    @JvmStatic
    fun info(msg: String, throwable: Throwable) {
        log(INFO, msg, throwable)
    }

    @JvmStatic
    fun warning(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    @JvmStatic
    fun warning(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    @JvmStatic
    fun warning(msg: String) {
        log(WARN, msg, null)
    }

    @JvmStatic
    fun warning(msg: String, throwable: Throwable) {
        log(WARN, msg, throwable)
    }

    @JvmStatic
    fun error(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    @JvmStatic
    fun error(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    @JvmStatic
    fun error(msg: String) {
        log(ERROR, msg, null)
    }

    @JvmStatic
    fun error(msg: String, throwable: Throwable) {
        log(ERROR, msg, throwable)
    }

    @JvmStatic
    fun wtf(obj: Any) {
        log(DEBUG, ObjectHelper.jsonify(obj), null)
    }

    @JvmStatic
    fun wtf(obj: Any, throwable: Throwable) {
        log(DEBUG, ObjectHelper.jsonify(obj), throwable)
    }

    @JvmStatic
    fun wtf(msg: String) {
        log(WTF, msg, null)
    }

    @JvmStatic
    fun wtf(msg: String, throwable: Throwable) {
        log(WTF, msg, throwable)
    }

    @JvmStatic
    fun wtf(throwable: Throwable) {
        log(WTF, throwable.message, throwable)
    }

}
