package me.shkschneider.skeleton.android.app

import android.app.Dialog
import android.view.WindowManager
import me.shkschneider.skeleton.kotlin.content.Listener

fun Dialog.dimBehind(dim: Boolean) {
    if (dim) {
        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    } else {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}

fun Dialog.cancelable(cancelable: Boolean, canceledOnTouchOutside: Boolean, onDismissListener: Listener? = null) {
    setCancelable(cancelable)
    setCanceledOnTouchOutside(canceledOnTouchOutside)
    onDismissListener?.let { listener -> {
        setOnDismissListener { listener() }
    }}
}
