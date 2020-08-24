package me.shkschneider.skeleton.android.app

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.UiThread
import androidx.fragment.app.DialogFragment

abstract class SkeletonDialog : DialogFragment() {

    val alive: Boolean
        get() = dialog?.isShowing ?: false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).also { dialog ->
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

    @UiThread
    fun dimBehind(dim: Boolean) {
        dialog?.window?.let { window ->
            if (dim) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
    }

}
