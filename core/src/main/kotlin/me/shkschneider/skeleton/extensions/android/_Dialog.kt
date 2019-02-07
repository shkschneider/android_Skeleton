package me.shkschneider.skeleton.extensions.android

import android.app.Dialog
import android.view.WindowManager

fun Dialog.dimBehind(dim: Boolean) {
    if (dim) {
        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    } else {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}

fun Dialog.cancelable(cancelable: Boolean, canceledOnTouchOutside: Boolean) {
    setCancelable(cancelable)
    setCanceledOnTouchOutside(canceledOnTouchOutside)
}