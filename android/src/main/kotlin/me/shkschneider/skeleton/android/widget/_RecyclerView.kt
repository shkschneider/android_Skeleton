package me.shkschneider.skeleton.android.widget

import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

val RecyclerView.isScrollable: Boolean
    get() {
        val childHeight = children.first().height
        return height < childHeight + paddingTop + paddingBottom
    }

fun RecyclerView.disableNestedScrollingIfUseless() {
    ViewCompat.setNestedScrollingEnabled(this, isScrollable)
}
