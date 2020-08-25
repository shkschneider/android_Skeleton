package me.shkschneider.skeleton.android.provider

import android.annotation.SuppressLint
import android.content.Context

/**
 * applicationContext()
 */
@SuppressLint("StaticFieldLeak")
object ContextProvider {

    @Suppress("ObjectPropertyName")
    private var _context: Context? = null

    fun applicationContext(context: Context? = null) =
        context?.also { _context = context.applicationContext } ?: _context ?: throw KotlinNullPointerException("Context was not set!")

}
