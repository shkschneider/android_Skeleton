package me.shkschneider.skeleton.helper

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.view.View

object ActivityTransitionHelper {

    fun tag(view: View, transitionName: String) {
        ViewCompat.setTransitionName(view, transitionName)
    }

    fun transition(activity: Activity, intent: Intent, vararg pairs: Pair<View, String>) {
        val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *pairs)
        ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle())
    }

}
