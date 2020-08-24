package me.shkschneider.skeleton.extensions

import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.widget.NestedScrollView

val NestedScrollView.isContentScrollable: Boolean
    get() {
        val childHeight = children.first().height
        return height < childHeight + paddingTop + paddingBottom
    }

fun NestedScrollView.disableNestedScrollingIfNothingToScroll() {
    ViewCompat.setNestedScrollingEnabled(this, isContentScrollable)
}
