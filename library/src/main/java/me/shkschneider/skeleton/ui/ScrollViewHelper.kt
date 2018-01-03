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

}
