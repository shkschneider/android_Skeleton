package me.shkschneider.skeleton.ui

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.ListView
import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.helper.ContextHelper

// <https://github.com/makovkastar/FloatingActionButton>
object FloatingActionButtonCompat {

    private val THRESHOLD = 5

    fun show(floatingActionButton: FloatingActionButton, animate: Boolean) {
        if (floatingActionButton.visibility == View.VISIBLE) return
        floatingActionButton.visibility = View.VISIBLE
        if (! animate) return
        floatingActionButton.startAnimation(AnimationUtils.loadAnimation(ContextHelper.applicationContext(), me.shkschneider.skeleton.R.anim.sk_scale_up))
    }

    fun hide(floatingActionButton: FloatingActionButton, animate: Boolean) {
        if (floatingActionButton.visibility == View.GONE) return
        if (! animate) {
            floatingActionButton.visibility = View.GONE
            return
        }
        val animation = AnimationUtils.loadAnimation(ContextHelper.applicationContext(), R.anim.sk_scale_down)
        animation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // Ignore
            }
            override fun onAnimationEnd(animation: Animation) {
                floatingActionButton.visibility = View.GONE
            }
            override fun onAnimationRepeat(animation: Animation) {
                // Ignore
            }
        })
        floatingActionButton.startAnimation(animation)
    }

    fun colors(floatingActionButton: FloatingActionButton, @ColorInt normal: Int, @ColorInt ripple: Int) {
        // app:borderWidth="0dp"
        floatingActionButton.backgroundTintList = ColorStateList.valueOf(normal)
        floatingActionButton.rippleColor = ripple
    }

    fun absListView(floatingActionButton: FloatingActionButton, listView: ListView, onScrollListener: AbsListView.OnScrollListener?) {
        listView.setOnScrollListener(object: AbsListView.OnScrollListener {
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
                            floatingActionButton.hide()
                        } else {
                            floatingActionButton.show()
                        }
                    }
                    lastScrollY = newScrollY
                } else {
                    if (firstVisibleItem > previousFirstVisibleItem) {
                        floatingActionButton.hide()
                    } else {
                        floatingActionButton.show()
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

    fun myScrollView(floatingActionButton: FloatingActionButton, myScrollView: MyScrollView) {
        myScrollView.setOnScrollViewListener(object: MyScrollView.OnScrollViewListener {
            private var mLastScrollY = 0
            override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
                val isSignificantDelta = Math.abs(t - mLastScrollY) > THRESHOLD
                if (isSignificantDelta) {
                    if (t > mLastScrollY) {
                        floatingActionButton.hide()
                    } else {
                        floatingActionButton.show()
                    }
                }
                mLastScrollY = t
            }
        })
    }

    // <http://stackoverflow.com/a/35981886>
    fun recyclerView(floatingActionButton: FloatingActionButton, recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    floatingActionButton.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val isSignificantDelta = Math.abs(dy) > 5
                if (/*dy < 0 || */isSignificantDelta && dy > 0) {
                    floatingActionButton.hide()
                }
            }
        })
    }

}
