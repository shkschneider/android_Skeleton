package me.shkschneider.skeleton.android.core.helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import me.shkschneider.skeleton.android.log.Logger

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
        ContextHelper.context = context.applicationContext
    }

    fun applicationContext(): Context {
        return context ?: throw KotlinNullPointerException("Context was not set!")
    }

}
