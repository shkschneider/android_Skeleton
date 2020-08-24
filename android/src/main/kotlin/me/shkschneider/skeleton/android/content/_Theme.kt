package me.shkschneider.skeleton.android.content

import android.content.res.Resources
import android.util.TypedValue
import me.shkschneider.skeleton.android.R

fun Resources.Theme.accentColor(): Int =
        with(TypedValue()) {
            resolveAttribute(R.attr.colorAccent, this, true)
            return data
        }

fun Resources.Theme.primaryColor(): Int =
        with(TypedValue()) {
            resolveAttribute(R.attr.colorPrimary, this, true)
            return data
        }

fun Resources.Theme.primaryColorDark(): Int =
        with(TypedValue()) {
            resolveAttribute(R.attr.colorPrimaryDark, this, true)
            return data
        }
