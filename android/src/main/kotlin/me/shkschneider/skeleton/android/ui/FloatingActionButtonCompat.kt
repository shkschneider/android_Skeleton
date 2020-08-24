package me.shkschneider.skeleton.android.ui

import android.content.res.ColorStateList
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.ListView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.shkschneider.skeleton.android.R
import me.shkschneider.skeleton.android.core.helper.ContextHelper

object FloatingActionButtonCompat {

    private const val THRESHOLD = 5

    fun show(floatingActionButton: FloatingActionButton, animate: Boolean = true) =
            with(floatingActionButton) {
                if (visibility == View.VISIBLE) return
                visibility(this, View.VISIBLE)
                if (!animate) return
                startAnimation(AnimationUtils.loadAnimation(ContextHelper.applicationContext(), R.anim.sk_scale_up))
            }

    fun hide(floatingActionButton: FloatingActionButton, animate: Boolean = true) =
            with(floatingActionButton) {
                if (visibility == View.GONE) return
                if (!animate) {
                    visibility(this, View.GONE)
                    return
                }
                startAnimation(AnimationUtils.loadAnimation(ContextHelper.applicationContext(), R.anim.sk_scale_down).apply {
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {
                            // Ignore
                        }

                        override fun onAnimationEnd(animation: Animation) {
                            visibility(this@with, View.GONE)
                        }

                        override fun onAnimationRepeat(animation: Animation) {
                            // Ignore
                        }
                    })
                })
            }

    fun colors(floatingActionButton: FloatingActionButton, @ColorInt normal: Int, @ColorInt ripple: Int) =
            with(floatingActionButton) {
                // app:borderWidth="0dp"
                backgroundTintList = ColorStateList.valueOf(normal)
                rippleColor = ripple
            }

    fun absListView(floatingActionButton: FloatingActionButton, listView: ListView, onScrollListener: AbsListView.OnScrollListener?) =
            with(floatingActionButton) {
                listView.setOnScrollListener(object : AbsListView.OnScrollListener {
                    private var lastScrollY = 0
                    private var previousFirstVisibleItem = 0
                    private val topItemScrollY: Int
                        get() {
                            val topChild = listView.getChildAt(0) ?: return 0
                            return topChild.top
                        }

                    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                        if (totalItemCount == 0) {
                            onScrollListener?.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount)
                            return
                        }
                        if (firstVisibleItem == previousFirstVisibleItem) {
                            val newScrollY = topItemScrollY
                            val isSignificantDelta = Math.abs(lastScrollY - newScrollY) > THRESHOLD
                            if (isSignificantDelta) {
                                if (lastScrollY > newScrollY) {
                                    FloatingActionButtonCompat.hide(this@with)
                                } else {
                                    FloatingActionButtonCompat.show(this@with)
                                }
                            }
                            lastScrollY = newScrollY
                        } else {
                            if (firstVisibleItem > previousFirstVisibleItem) {
                                FloatingActionButtonCompat.hide(this@with)
                            } else {
                                FloatingActionButtonCompat.show(this@with)
                            }
                            lastScrollY = topItemScrollY
                            previousFirstVisibleItem = firstVisibleItem
                        }
                        onScrollListener?.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount)
                    }

                    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                        onScrollListener?.onScrollStateChanged(view, scrollState)
                    }
                })
            }

    // <http://stackoverflow.com/a/35981886>
    fun recyclerView(floatingActionButton: FloatingActionButton, recyclerView: RecyclerView) =
            with(floatingActionButton) {
                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            FloatingActionButtonCompat.show(this@with)
                        }
                        super.onScrollStateChanged(recyclerView, newState)
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val isSignificantDelta = Math.abs(dy) > 5
                        if (/*dy < 0 || */isSignificantDelta && dy > 0) {
                            FloatingActionButtonCompat.hide(this@with)
                        }
                    }
                })
            }

    private fun visibility(floatingActionButton: FloatingActionButton, visibility: Int) {
        (floatingActionButton as View).visibility = visibility
    }

}
