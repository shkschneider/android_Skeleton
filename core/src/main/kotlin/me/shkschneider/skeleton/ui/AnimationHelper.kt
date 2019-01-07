package me.shkschneider.skeleton.ui

import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.core.animation.addListener
import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.Logger

object AnimationHelper {

    fun revealOn(view: View) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View,
                                            left: Int, top: Int, right: Int, bottom: Int,
                                            oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    view.removeOnLayoutChangeListener(this)
                    val width = view.measuredWidth / 2
                    val height = view.measuredHeight / 2
                    val radius = Math.max(view.width, view.height) / 2
                    if (Build.VERSION.SDK_INT >= 21) {
                        ViewAnimationUtils.createCircularReveal(view, width, height, 1.toFloat(), radius.toFloat()).run {
                            // setInterpolator(new AccelerateDecelerateInterpolator());
                            duration = ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium).toLong()
                            view.visibility = View.VISIBLE
                            start()
                        }
                    }
                }
            })
        } else {
            view.visibility = View.VISIBLE
        }
    }

    fun revealOff(view: View) {
        if (Build.VERSION.SDK_INT >= 21) {
            val width = view.measuredWidth / 2
            val height = view.measuredHeight / 2
            val radius = view.width / 2
            ViewAnimationUtils.createCircularReveal(view, width, height, radius.toFloat(), 1.toFloat()).run {
                // setInterpolator(new AccelerateDecelerateInterpolator());
                duration = ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium).toLong()
                addListener {
                    view.visibility = View.INVISIBLE
                }
                start()
            }
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    fun load(@AnimRes anim: Int): Animation? {
        try {
            return AnimationUtils.loadAnimation(ContextHelper.applicationContext(), anim)
        } catch (e: Resources.NotFoundException) {
            Logger.wtf(e)
            return null
        }
    }

}
