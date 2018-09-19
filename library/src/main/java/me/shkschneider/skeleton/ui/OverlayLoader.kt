package me.shkschneider.skeleton.ui

import android.app.Dialog
import android.os.Bundle
import android.support.annotation.UiThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.SkeletonDialog
import me.shkschneider.skeleton.extensions.TAG

class OverlayLoader : SkeletonDialog() {

    companion object {

        fun show(activity: SkeletonActivity) : OverlayLoader? {
            if (activity.alive()) {
                val overlayLoader = OverlayLoader()
                overlayLoader.showNow(activity.supportFragmentManager, TAG)
                // IllegalStateException: FragmentManager is already executing transactions
                return overlayLoader
            }
            return null
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also { dialog ->
            isCancelable = false
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
        progressBar.isIndeterminate = true
        return progressBar
    }

    @UiThread
    fun hide(activity: SkeletonActivity): Boolean {
        if (activity.alive()) {
            dismiss()
            return true
        }
        return false
    }

}
