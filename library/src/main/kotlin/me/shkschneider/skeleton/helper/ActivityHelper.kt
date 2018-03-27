package me.shkschneider.skeleton.helper

import android.app.ActivityOptions

object ActivityHelper {

    // <https://developer.android.com/reference/android/app/ActivityOptions.html>

    fun pendingTransition(animIn: Int = android.R.anim.fade_in, animOut: Int = android.R.anim.fade_out): ActivityOptions {
        return ActivityOptions.makeCustomAnimation(ContextHelper.applicationContext(), animIn, animOut)
    }

}
