package me.shkschneider.skeleton

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Window
import android.view.WindowManager

abstract class SkeletonDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        showsDialog = false
        return dialog
    }

    fun dimBehind(dim: Boolean) {
        if (dim) {
            dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

//    override fun onStart() {
//        super.onStart()
//        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    }

}
