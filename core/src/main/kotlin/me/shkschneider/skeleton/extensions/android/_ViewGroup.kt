package me.shkschneider.skeleton.extensions.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

inline val ViewGroup.views
    get() = (0 until childCount).map { getChildAt(it) }

fun ViewGroup.addOrUpdateView(view: View) {
    // Avoids IllegalStateException for already added views
    if (view.parent != null) {
       this.removeView(view)
    }
    this.addView(view)
}
