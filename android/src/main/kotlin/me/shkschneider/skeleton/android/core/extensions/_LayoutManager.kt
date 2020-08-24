package me.shkschneider.skeleton.android.core.extensions

import androidx.recyclerview.widget.LinearLayoutManager

val LinearLayoutManager.visibleRange: IntRange
    get() = findFirstVisibleItemPosition()..findLastVisibleItemPosition()
