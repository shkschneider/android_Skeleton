package me.shkschneider.skeleton.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import me.shkschneider.skeleton.java.SkHide
import java.util.*

object ViewHelper {

    val ANDROIDXML = "http://schemas.android.com/apk/res/android"
    val RESAUTOXML = "http://schemas.android.com/apk/res-auto"
    @SkHide
    val CONTENT = Window.ID_ANDROID_CONTENT
    @SkHide
    val NO_ID = View.NO_ID

    fun <T : View> content(activity: Activity): T {
        return activity.findViewById(CONTENT)
    }

    fun children(view: View): List<View> {
        val viewGroup = view as ViewGroup
        return (0 until viewGroup.childCount).mapTo(ArrayList<View>()) { viewGroup.getChildAt(it) }
    }

    fun show(view: View): Boolean {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
            return true
        }
        return false
    }

    fun hide(view: View): Boolean {
        if (view.visibility != View.GONE) {
            view.visibility = View.GONE
            return true
        }
        return false
    }

    // TODO fun visibilityListener(view: View)

}
