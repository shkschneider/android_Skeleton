package me.shkschneider.skeleton.extensions.android

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

val LinearLayoutManager.visibleRange: IntRange
    get() = findFirstVisibleItemPosition()..findLastVisibleItemPosition()
