package me.shkschneider.skeleton.extensions.android

import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import kotlinx.coroutines.NonCancellable.children

val NestedScrollView.isContentScrollable: Boolean
    get() {
        val childHeight = children.first().height
        return height < childHeight + paddingTop + paddingBottom
    }

fun NestedScrollView.disableNestedScrollingIfNothingToScroll() {
    ViewCompat.setNestedScrollingEnabled(this, isContentScrollable)
}
