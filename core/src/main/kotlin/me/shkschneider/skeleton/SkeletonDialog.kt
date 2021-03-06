package me.shkschneider.skeleton

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.UiThread
import androidx.fragment.app.DialogFragment

abstract class SkeletonDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    @UiThread
    fun dimBehind(dim: Boolean) {
        dialog.window?.let { window ->
            if (dim) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
    }

    fun alive(): Boolean {
        return dialog.isShowing
    }

}
