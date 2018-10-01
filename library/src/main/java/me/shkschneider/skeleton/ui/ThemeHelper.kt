package me.shkschneider.skeleton.ui

import android.util.TypedValue
import androidx.annotation.ColorLong

import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.helper.ContextHelper

object ThemeHelper {

    private fun color(@ColorLong id: Int): Int {
        val typedValue = TypedValue()
        ContextHelper.applicationContext().theme.resolveAttribute(id, typedValue, true)
        return typedValue.data
    }

    fun accentColor(): Int {
        return color(R.attr.colorAccent)
    }

    fun primaryColor(): Int {
        return color(R.attr.colorPrimary)
    }

    fun primaryColorDark(): Int {
        return color(R.attr.colorPrimaryDark)
    }

}
