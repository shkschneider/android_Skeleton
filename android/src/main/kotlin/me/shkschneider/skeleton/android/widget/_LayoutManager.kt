package me.shkschneider.skeleton.android.widget

import androidx.recyclerview.widget.LinearLayoutManager

val LinearLayoutManager.visibleRange: IntRange
    get() = findFirstVisibleItemPosition()..findLastVisibleItemPosition()
