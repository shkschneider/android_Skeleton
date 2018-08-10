package me.shkschneider.skeleton.ui

import android.view.View
import android.widget.ScrollView

object ScrollViewHelper {

    fun top(scrollView: ScrollView) {
        scrollView.fullScroll(View.FOCUS_UP)
    }

    fun bottom(scrollView: ScrollView) {
        scrollView.fullScroll(View.FOCUS_DOWN)
    }

    fun pageUp(scrollView: ScrollView) {
        scrollView.pageScroll(View.FOCUS_UP)
    }

    fun pageDown(scrollView: ScrollView) {
        scrollView.pageScroll(View.FOCUS_DOWN)
    }

    // <http://benjii.me/2010/08/endless-scrolling-listview-in-android/>
    fun hasMoreItems(firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int, threshold: Int): Boolean {
        return totalItemCount - visibleItemCount <= firstVisibleItem + threshold
    }

}
