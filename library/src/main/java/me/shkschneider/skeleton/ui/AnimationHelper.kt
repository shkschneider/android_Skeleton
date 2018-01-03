package me.shkschneider.skeleton.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.View
import android.view.ViewAnimationUtils

import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ApplicationHelper

@SuppressLint("NewApi")
object AnimationHelper {

    fun revealOn(view: View) {
        if (AndroidHelper.api() < AndroidHelper.API_21) {
            view.visibility = View.VISIBLE
        } else {
            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View,
                                            left: Int, top: Int, right: Int, bottom: Int,
                                            oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    view.removeOnLayoutChangeListener(this)
                    val width = view.measuredWidth / 2
                    val height = view.measuredHeight / 2
                    val radius = Math.max(view.width, view.height) / 2
                    val animator = ViewAnimationUtils.createCircularReveal(view, width, height, 0f, radius.toFloat())
                    // animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.duration = ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium).toLong()
                    view.visibility = View.VISIBLE
                    animator.start()
                }
            })
        }
    }

    fun revealOff(view: View) {
        if (AndroidHelper.api() < AndroidHelper.API_21) {
            view.visibility = View.INVISIBLE
            return
        } else {
            val width = view.measuredWidth / 2
            val height = view.measuredHeight / 2
            val radius = view.width / 2
            val animator = ViewAnimationUtils.createCircularReveal(view, width, height, radius.toFloat(), 0f)
            // animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.duration = ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium).toLong()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.INVISIBLE
                }
            })
            animator.start()
        }
    }

}
