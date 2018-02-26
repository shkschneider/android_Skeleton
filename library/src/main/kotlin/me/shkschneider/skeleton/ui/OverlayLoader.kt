package me.shkschneider.skeleton.ui

import android.widget.FrameLayout
import android.widget.ProgressBar
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*

class OverlayLoader : Fragment() {

    companion object {

        fun show(activity: AppCompatActivity) : OverlayLoader {
            val overlayLoader = OverlayLoader()
            activity.supportFragmentManager.beginTransaction().add(ViewHelper.CONTENT, overlayLoader).commit()
            return overlayLoader
        }

        @Deprecated("Use on instance")
        fun hide(activity: AppCompatActivity, overlayLoader: OverlayLoader) {
            activity.supportFragmentManager.beginTransaction().remove(overlayLoader).commit()
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val progressBar = ProgressBar(context)
        if (container is FrameLayout) {
            val layoutParams = FrameLayout.LayoutParams(100, 100, Gravity.CENTER)
            progressBar.layoutParams = layoutParams
        }
        return progressBar
    }

    fun hide(activity: AppCompatActivity) {
        activity.supportFragmentManager.beginTransaction().remove(this).commit()
    }

}
