package me.shkschneider.skeleton.ui

import android.view.Gravity
import android.widget.Toast
import androidx.annotation.UiThread

import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.Logger

object Toaster {

    @UiThread
    fun show(msg: String, duration: Int = Toast.LENGTH_LONG) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        Toast.makeText(ContextHelper.applicationContext(), msg, duration)?.let {
            it.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL, 0, 0)
            it.show()
        }
    }

}
