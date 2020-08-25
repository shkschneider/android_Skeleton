package me.shkschneider.skeleton.android.view

import android.view.View
import androidx.annotation.UiThread
import androidx.appcompat.widget.TooltipCompat

object Tooltips {

    @UiThread
    fun set(view: View, msg: CharSequence) {
        TooltipCompat.setTooltipText(view, msg)
    }

}
