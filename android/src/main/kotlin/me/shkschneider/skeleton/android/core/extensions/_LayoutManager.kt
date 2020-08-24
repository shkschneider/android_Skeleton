package me.shkschneider.skeleton.extensions

import androidx.recyclerview.widget.LinearLayoutManager

val LinearLayoutManager.visibleRange: IntRange
    get() = findFirstVisibleItemPosition()..findLastVisibleItemPosition()
