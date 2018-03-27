package me.shkschneider.skeleton.ui

import android.widget.Toast

import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.Logger
import me.shkschneider.skeleton.helper.ThreadHelper

object Toaster {

    fun show(msg: String, duration: Int = Toast.LENGTH_LONG) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        ThreadHelper.foregroundThread(Runnable { Toast.makeText(ContextHelper.applicationContext(), msg, duration).show() })
    }

}
