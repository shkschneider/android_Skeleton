package me.shkschneider.skeleton.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils

import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ApplicationHelper

object AnimationHelper {

    fun revealOn(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View,
                                            left: Int, top: Int, right: Int, bottom: Int,
                                            oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    view.removeOnLayoutChangeListener(this)
                    val width = view.measuredWidth / 2
                    val height = view.measuredHeight / 2
                    val radius = Math.max(view.width, view.height) / 2
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        with(ViewAnimationUtils.createCircularReveal(view, width, height, 1.toFloat(), radius.toFloat())) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val width = view.measuredWidth / 2
            val height = view.measuredHeight / 2
            val radius = view.width / 2
            with(ViewAnimationUtils.createCircularReveal(view, width, height, radius.toFloat(), 1.toFloat())) {
                // setInterpolator(new AccelerateDecelerateInterpolator());
                duration = ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium).toLong()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        view.visibility = View.INVISIBLE
                    }
                })
                start()
            }
        } else {
            view.visibility = View.INVISIBLE
        }
    }

}
