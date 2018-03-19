package me.shkschneider.skeleton.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context

/**
 * applicationContext()
 */
@SuppressLint("StaticFieldLeak")
object ContextHelper {

    private var context: Context? = null

    fun applicationContext(context: Context) {
        if (context !is Application && context !is Activity) {
            Logger.warning("Context is supposed to be Application or Activity based!")
        }
        this.context = context.applicationContext
    }

    fun applicationContext(): Context {
        context ?: run {
            throw NullPointerException("Context was not set!")
        }
        return context!!
    }

}
