package me.shkschneider.skeleton.extensions.android

import android.view.View
import android.view.ViewGroup

@Deprecated("AndroidX View.children", ReplaceWith(".children"))
inline val ViewGroup.views
    get() = (0 until childCount).map { getChildAt(it) }

fun ViewGroup.addOrUpdateView(view: View) {
    // Avoids IllegalStateException for already added views
    if (view.parent != null) {
        this.removeView(view)
    }
    this.addView(view)
}

@Deprecated("AndroidX View.children", ReplaceWith(".children.forEach"))
fun ViewGroup.forEach(action: (View) -> Unit) {
    for (position in 0 until childCount) {
        action(getChildAt(position))
    }
}

@Deprecated("AndroidX View.children", ReplaceWith(".children.forEachIndexed"))
fun ViewGroup.forEachIndexed(action: (View, Int) -> Unit) {
    for (position in 0 until childCount) {
        action(getChildAt(position), position)
    }
}

fun ViewGroup.isEmpty() = childCount == 0

fun ViewGroup.isNotEmpty() = !isEmpty()
