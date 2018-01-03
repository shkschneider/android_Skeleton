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

    private var _context: Context? = null

    fun applicationContext(context: Context) {
        if (context !is Application && context !is Activity) {
            LogHelper.warning("Context is supposed to be Application or Activity based!")
        }
        _context = context
    }

    fun applicationContext(): Context {
        if (_context == null) {
            throw NullPointerException("Context was not set!")
        }
        return _context!!
    }

}
