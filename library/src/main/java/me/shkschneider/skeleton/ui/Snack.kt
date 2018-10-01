package me.shkschneider.skeleton.ui

import android.view.View
import androidx.annotation.UiThread
import com.google.android.material.snackbar.Snackbar
import me.shkschneider.skeleton.extensions.then

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
        snackBar.duration = duration ?: (action.isNullOrBlank()) then Snackbar.LENGTH_SHORT ?: Snackbar.LENGTH_INDEFINITE
        snackBar.show()
    }

}
