package me.shkschneider.skeleton.extensions.android

import android.app.Activity
import android.content.Intent

inline fun <reified T> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

inline fun <reified T: Activity> Activity.startActivityForResult(requestCode: Int, block: Intent.() -> Unit = {}) =
        startActivityForResult(Intent(this, T::class).apply {
            block(this)
        }, requestCode)