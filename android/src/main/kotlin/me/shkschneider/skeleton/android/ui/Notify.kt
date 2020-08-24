package me.shkschneider.skeleton.android.ui

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import me.shkschneider.skeleton.android.R
import me.shkschneider.skeleton.android.core.helper.AndroidHelper
import me.shkschneider.skeleton.android.core.helper.ContextHelper
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.kotlin.kotlinx.Listener

object Notify {

    @UiThread
    fun toast(msg: String, duration: Int = Toast.LENGTH_LONG) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        Toast.makeText(ContextHelper.applicationContext(), msg, duration)?.apply {
            show()
        }
    }

    @UiThread
    @SuppressLint("WrongConstant")
    fun snackBar(view: View, msg: String, action: String? = null, listener: Listener? = null, duration: Int = Snackbar.LENGTH_SHORT) {
        if (msg.isBlank()) {
            Logger.warning("Message was NULL")
        }
        with(Snackbar.make(view, msg, duration)) {
            // view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action).maxLines = 2
            listener?.let {
                setAction(action) {
                    listener()
                }
            }
            setDuration(duration)
            if (AndroidHelper.api() >= AndroidHelper.API_23) {
                material()
            }
            show()
        }
    }

    // TODO test
    @SuppressLint("ResourceType")
    fun chip(@ColorRes color: Int, text: String, @DrawableRes drawable: Int? = null): Chip =
            Chip(ContextHelper.applicationContext()).apply {
                setText(text)
                if (drawable != null) {
                    setChipDrawable(ChipDrawable.createFromResource(context, drawable))
                }
                isClickable = false
                isLongClickable = false
                chipBackgroundColor = ContextCompat.getColorStateList(context, color)
            }

}

// <https://medium.com/@Tgo1014/creating-googles-new-snackbar-b0fe8db6c0eb>
fun Snackbar.material() {
    view.layoutParams = (view.layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(12, 12, 12, 12)
    }
    view.background = ContextCompat.getDrawable(ContextHelper.applicationContext(), R.drawable.sk_snackbar)
    ViewCompat.setElevation(view, ContextHelper.applicationContext().resources.getDimension(R.dimen.sk_elevation6))
}
