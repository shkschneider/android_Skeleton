package me.shkschneider.skeleton.extensions

import android.app.Activity
import android.content.res.Configuration

fun Activity.portrait(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

fun Activity.landscape(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}
