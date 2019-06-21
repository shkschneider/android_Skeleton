package me.shkschneider.skeleton.extensions.android

import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonCancellable.children

val RecyclerView.isScrollable: Boolean
    get() {
        val childHeight = children.first().height
        return height < childHeight + paddingTop + paddingBottom
    }

fun RecyclerView.disableNestedScrollingIfUseless() {
    ViewCompat.setNestedScrollingEnabled(this, isScrollable)
}
