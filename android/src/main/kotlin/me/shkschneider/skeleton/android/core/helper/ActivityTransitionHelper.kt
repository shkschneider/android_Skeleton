package me.shkschneider.skeleton.android.core.helper

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
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
