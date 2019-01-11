package me.shkschneider.skeleton.uix

import android.view.View
import androidx.annotation.UiThread
import com.google.android.material.snackbar.Snackbar
import me.shkschneider.skeleton.helperx.Logger
import me.shkschneider.skeleton.kotlinx.Listener

object Snack {

    @UiThread
    fun bar(view: View, msg: String, action: String? = null, listener: Listener? = null, duration: Int? = null) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        // view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action).maxLines = 2
        listener?.let {
            snackBar.setAction(action, View.OnClickListener {
                listener()
            })
        }
        snackBar.duration = when {
            duration != null -> duration
            !action.isNullOrBlank() -> Snackbar.LENGTH_INDEFINITE
            else -> Snackbar.LENGTH_SHORT
        }
        snackBar.show()
    }

}