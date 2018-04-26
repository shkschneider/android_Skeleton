package me.shkschneider.skeleton.ui

import android.support.annotation.UiThread
import android.widget.Toast

import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.Logger

object Toaster {

    @UiThread
    fun show(msg: String, duration: Int = Toast.LENGTH_LONG) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        Toast.makeText(ContextHelper.applicationContext(), msg, duration).show()
    }

}
