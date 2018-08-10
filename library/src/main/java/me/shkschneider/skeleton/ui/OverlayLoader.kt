package me.shkschneider.skeleton.ui

import android.os.Bundle
import android.support.annotation.UiThread
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.SkeletonDialog

class OverlayLoader : SkeletonDialog() {

    companion object {

        fun show(activity: SkeletonActivity) : OverlayLoader? {
            if (activity.alive()) {
                val overlayLoader = OverlayLoader()
                activity.supportFragmentManager.beginTransaction().add(ViewHelper.CONTENT, overlayLoader).commit()
                return overlayLoader
            }
            return null
        }

        @Deprecated("Obsolete.", ReplaceWith("OverlayLoader.hide()"))
        fun hide(activity: SkeletonActivity, overlayLoader: OverlayLoader) {
            overlayLoader.hide(activity)
            activity.supportFragmentManager.beginTransaction().remove(overlayLoader).commit()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val progressBar = ProgressBar(context)
        if (container is FrameLayout) {
            val size = resources.getDimensionPixelSize(R.dimen.spaceMedium)
            val layoutParams = FrameLayout.LayoutParams(size, size, Gravity.CENTER)
            progressBar.layoutParams = layoutParams
        }
        return progressBar
    }

    @UiThread
    fun hide(activity: SkeletonActivity): Boolean {
        if (activity.alive()) {
            activity.supportFragmentManager.beginTransaction().remove(this).commit()
            return true
        }
        return false
    }

}
