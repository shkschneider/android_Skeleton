package me.shkschneider.skeleton.extensions

import android.content.res.Configuration
import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.helper.ScreenHelper

fun SkeletonActivity.portrait(): Boolean {
    return ScreenHelper.orientation(this) != Configuration.ORIENTATION_LANDSCAPE
}

fun SkeletonActivity.landscape(): Boolean {
    return ScreenHelper.orientation(this) == Configuration.ORIENTATION_LANDSCAPE
}

@Deprecated("Use recreate()")
fun SkeletonActivity.restart() {
    recreate()
}
