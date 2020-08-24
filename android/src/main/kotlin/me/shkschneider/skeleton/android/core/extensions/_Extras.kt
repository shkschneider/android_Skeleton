package me.shkschneider.skeleton.extensions

import android.app.Activity
import androidx.fragment.app.Fragment

// val value by extra<String>("key")

inline fun <reified T> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

inline fun <reified T> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}
