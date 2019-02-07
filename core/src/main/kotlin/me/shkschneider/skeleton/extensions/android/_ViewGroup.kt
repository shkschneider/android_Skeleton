package me.shkschneider.skeleton.extensions.android

import android.view.View
import android.view.ViewGroup

//inline val ViewGroup.views
//    get() = (0 until childCount).map { getChildAt(it) }

fun ViewGroup.addOrUpdateView(view: View) {
    // Avoids IllegalStateException for already added views
    if (view.parent != null) {
        this.removeView(view)
    }
    this.addView(view)
}
