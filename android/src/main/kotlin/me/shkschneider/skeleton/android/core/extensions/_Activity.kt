package me.shkschneider.skeleton.extensions

import android.app.Activity
import android.content.ContextWrapper
import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.annotation.UiThread

inline fun <reified T : Activity> ContextWrapper.startActivity(block: Intent.() -> Unit = {}) =
        startActivity(Intent(this, T::class).apply {
            block(this)
        })

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, block: Intent.() -> Unit = {}) =
        startActivityForResult(Intent(this, T::class).apply {
            block(this)
        }, requestCode)

@UiThread
fun Activity.block() =
        window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

@UiThread
fun Activity.unblock() =
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

fun Activity.contentView(): View =
        findViewById(ViewHelper.CONTENT)
