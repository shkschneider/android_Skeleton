package me.shkschneider.skeleton.extensions.android

import android.app.Activity
import android.content.ContextWrapper
import android.content.Intent

inline fun <reified T: Activity> ContextWrapper.startActivity(block: Intent.() -> Unit = {}) =
        startActivity(Intent(this, T::class).apply {
            block(this)
        })
