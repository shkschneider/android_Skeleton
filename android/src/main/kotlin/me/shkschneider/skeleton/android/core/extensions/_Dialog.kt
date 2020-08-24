package me.shkschneider.skeleton.android.core.extensions

import android.app.Dialog
import android.view.WindowManager

fun Dialog.dimBehind(dim: Boolean) {
    if (dim) {
        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    } else {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}

fun Dialog.cancelable(cancelable: Boolean, canceledOnTouchOutside: Boolean, onDismissListener: (() -> Unit)? = null) {
    setCancelable(cancelable)
    setCanceledOnTouchOutside(canceledOnTouchOutside)
    onDismissListener?.let { listener -> {
        setOnDismissListener { listener() }
    }}
}
