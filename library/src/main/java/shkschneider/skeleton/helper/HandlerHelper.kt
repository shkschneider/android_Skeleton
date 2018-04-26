package me.shkschneider.skeleton.helper

import android.os.Handler
import android.os.Looper

object HandlerHelper {

    fun main(): Handler {
        return Handler(Looper.getMainLooper())
    }

}
