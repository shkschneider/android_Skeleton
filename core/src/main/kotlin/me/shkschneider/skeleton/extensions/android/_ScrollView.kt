package me.shkschneider.skeleton.extensions.android

import android.view.View
import android.widget.ScrollView

fun ScrollView.scrollTop() =
        fullScroll(View.FOCUS_UP)

fun ScrollView.scrollBottom() =
        fullScroll(View.FOCUS_DOWN)

fun ScrollView.scrollUp() =
        pageScroll(View.FOCUS_UP)

fun ScrollView.scrollDown() =
        pageScroll(View.FOCUS_DOWN)

// <http://benjii.me/2010/08/endless-scrolling-listview-in-android/>
fun ScrollView.hasMoreItems(firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int, threshold: Int): Boolean =
        totalItemCount - visibleItemCount <= firstVisibleItem + threshold
