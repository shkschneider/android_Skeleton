package me.shkschneider.skeleton.ui

import android.support.annotation.UiThread
import android.support.design.widget.Snackbar
import android.view.View

import me.shkschneider.skeleton.helper.Logger

object Snack {

    @UiThread
    fun bar(view: View, msg: String, action: String? = null, onClickListener: View.OnClickListener? = null, duration: Int? = null) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        onClickListener?.let {
            snackBar.setAction(action, it)
        }
        snackBar.duration = duration ?: (if (action.isNullOrBlank()) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_INDEFINITE)
        snackBar.show()
    }

}
