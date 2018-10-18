package me.shkschneider.skeleton.uix

import android.widget.Toast
import androidx.annotation.UiThread

import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.Logger

object Toaster {

    @UiThread
    fun show(msg: String, duration: Int = Toast.LENGTH_LONG) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        Toast.makeText(ContextHelper.applicationContext(), msg, duration)?.apply {
            show()
        }
    }

}
