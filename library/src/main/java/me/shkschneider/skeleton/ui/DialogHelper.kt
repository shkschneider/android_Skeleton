package me.shkschneider.skeleton.ui

import android.app.Dialog
import android.view.Window
import android.view.WindowManager

object DialogHelper {

    fun dimBehind(window: Window, dim: Boolean) {
        if (dim) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    fun cancelable(dialog: Dialog, cancelable: Boolean, canceledOnTouchOutside: Boolean) {
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
    }

}
