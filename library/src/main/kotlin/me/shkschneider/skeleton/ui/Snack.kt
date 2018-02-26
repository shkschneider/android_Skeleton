package me.shkschneider.skeleton.ui

import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View

import me.shkschneider.skeleton.helper.LogHelper

object Snack {

    fun bar(view: View, msg: String, action: String? = null, onClickListener: View.OnClickListener? = null) {
        if (TextUtils.isEmpty(msg)) {
            LogHelper.warning("Message was NULL")
        }
        val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        if (!TextUtils.isEmpty(action) && onClickListener != null) {
            snackbar.setAction(action, onClickListener)
        }
        snackbar.show()
    }

}
