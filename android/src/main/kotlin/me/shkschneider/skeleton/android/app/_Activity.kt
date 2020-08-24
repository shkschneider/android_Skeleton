package me.shkschneider.skeleton.android.app

import android.app.Activity
import android.app.ActivityOptions
import android.content.ContextWrapper
import android.content.Intent
import android.view.View
import android.view.WindowManager
import androidx.annotation.UiThread
import me.shkschneider.skeleton.android.widget.ViewHelper
import me.shkschneider.skeleton.android.content.intent
import me.shkschneider.skeleton.android.provider.ContextProvider

// <https://developer.android.com/reference/android/app/ActivityOptions.html>
fun Activity.pendingTransition(animIn: Int = android.R.anim.fade_in, animOut: Int = android.R.anim.fade_out): ActivityOptions =
    ActivityOptions.makeCustomAnimation(this, animIn, animOut)

inline fun <reified T : Activity> ContextWrapper.startActivity(block: Intent.() -> Unit = {}) =
        startActivity(intent(this, T::class).apply {
            block(this)
        })

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, block: Intent.() -> Unit = {}) =
        startActivityForResult(intent(this, T::class).apply {
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
