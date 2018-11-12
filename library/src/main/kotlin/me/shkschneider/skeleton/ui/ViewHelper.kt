package me.shkschneider.skeleton.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.UiThread
import me.shkschneider.skeleton.SkeletonReceiver

object ViewHelper {

    val ANDROIDXML = "http://schemas.android.com/apk/res/android"
    val RESAUTOXML = "http://schemas.android.com/apk/res-auto"
    val CONTENT = Window.ID_ANDROID_CONTENT
    val NO_ID = View.NO_ID
    val RESULT_VISIBILITY = "VISIBILITY"

    fun <T : View> content(activity: Activity): T {
        return activity.findViewById(CONTENT)
    }

    fun children(view: View): List<View> {
        val viewGroup = view as ViewGroup
        return (0 until viewGroup.childCount).mapTo(ArrayList<View>()) { viewGroup.getChildAt(it) }
    }

    @UiThread
    fun show(view: View): Boolean {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
            return true
        }
        return false
    }

    @UiThread
    fun hide(view: View): Boolean {
        if (view.visibility != View.GONE) {
            view.visibility = View.GONE
            return true
        }
        return false
    }

    @UiThread
    fun block(activity: Activity) {
        activity.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    @UiThread
    fun unblock(activity: Activity) {
        activity.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    // <https://stackoverflow.com/a/32778292>
    fun visibilityListener(view: View, skeletonReceiver: SkeletonReceiver) {
        view.tag = view.visibility
        view.viewTreeObserver.addOnGlobalLayoutListener {
            if (view.tag != view.visibility) {
                view.tag = view.visibility
                skeletonReceiver.post(RESULT_VISIBILITY, view.visibility)
            }
        }
    }

}
