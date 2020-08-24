package me.shkschneider.skeleton.android.core.helper

import android.os.Handler
import android.os.Looper

object HandlerHelper {

    fun main(): Handler {
        return Handler(Looper.getMainLooper())
    }

}
