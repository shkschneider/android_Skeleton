package me.shkschneider.skeleton.helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * applicationContext()
 */
@SuppressLint("StaticFieldLeak")
object ContextHelper {

    private var context: Context? = null

    fun applicationContext(context: Context) {
        if (context !is Application) {
            Logger.warning("Context is supposed to be Application based!")
        }
        this.context = context.applicationContext
    }

    fun applicationContext(): Context {
        return context ?: throw NullPointerException("Context was not set!")
    }

}
