package me.shkschneider.skeleton.ui

import android.text.TextUtils
import android.widget.Toast

import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.helper.ThreadHelper

object Toaster {

    fun lengthShort(msg: String) {
        toast(msg, Toast.LENGTH_SHORT)
    }

    fun lengthLong(msg: String) {
        toast(msg, Toast.LENGTH_LONG)
    }

    private fun toast(msg: String, duration: Int) {
        if (TextUtils.isEmpty(msg)) {
            LogHelper.warning("Message was NULL")
        }
        ThreadHelper.foregroundThread(Runnable { Toast.makeText(ContextHelper.applicationContext(), msg, duration).show() })
    }

}
