package me.shkschneider.skeleton.ui

import android.support.design.widget.Snackbar
import android.view.View

import me.shkschneider.skeleton.helper.Logger

object Snack {

    fun bar(view: View, msg: String, action: String? = null, onClickListener: View.OnClickListener? = null, duration: Int? = null) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        if (! action.isNullOrBlank() && onClickListener != null) {
            snackBar.setAction(action, onClickListener)
            snackBar.duration = duration ?: Snackbar.LENGTH_INDEFINITE
        } else {
            snackBar.duration = duration ?: Snackbar.LENGTH_SHORT
        }
        snackBar.show()
    }

}
