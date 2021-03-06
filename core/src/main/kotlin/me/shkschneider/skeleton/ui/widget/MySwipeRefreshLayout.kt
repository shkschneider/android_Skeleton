package me.shkschneider.skeleton.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.AbsListView
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import me.shkschneider.skeleton.R

class MySwipeRefreshLayout(
        context: Context,
        attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    // Bugfix <https://code.google.com/p/android/issues/detail?id=87789>

    private var handleTouch = true

    // Prevents gesture conflicts with NavigationDrawer
    // <http://stackoverflow.com/a/24453194>

    private var touchSlop = 0
    private var width = 0.toFloat()
    private var declined = false

    init {
        isEnabled = false
        setColorSchemeResources(R.color.primaryColor)
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
    }

    override fun isEnabled(): Boolean {
        return super.isEnabled()
    }

    override fun isRefreshing(): Boolean {
        return super.isRefreshing()
    }

    // Bugfix <https://code.google.com/p/android/issues/detail?id=77712>

    override fun setRefreshing(refreshing: Boolean) {
        post { super.setRefreshing(refreshing) }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val action = motionEvent.action
        when (action) {
            MotionEvent.ACTION_DOWN -> handleTouch = false
            else -> {
                if (handleTouch) {
                    return super.onTouchEvent(motionEvent)
                }
                handleTouch = onInterceptTouchEvent(motionEvent)
                when (action) {
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> handleTouch = true
                }
            }
        }
        return true
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                width = MotionEvent.obtain(motionEvent).x
                declined = false
            }
            MotionEvent.ACTION_MOVE -> {
                val x = motionEvent.x
                val diff = Math.abs(x - width)
                if (declined || diff > touchSlop) {
                    declined = true
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(motionEvent)
    }

    companion object {

        // Prevents gesture conflicts with RecyclerView
        // <http://stackoverflow.com/q/25178329>

        fun recyclerViewCompat(mySwipeRefreshLayout: MySwipeRefreshLayout, recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager) {
            recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val idle = newState == RecyclerView.SCROLL_STATE_IDLE
                    val firstCompletelyVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0
                    mySwipeRefreshLayout.isEnabled = idle && firstCompletelyVisibleItem
                }
            })
        }

        // Prevents gesture conflicts with AbsListView
        // <http://nlopez.io/swiperefreshlayout-with-listview-done-right/>

        fun absListViewCompat(mySwipeRefreshLayout: MySwipeRefreshLayout, absListView: AbsListView) {
            absListView.setOnScrollListener(object: AbsListView.OnScrollListener {
                override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                    // Ignore
                }
                override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                    val topRowVerticalPosition = if (absListView.childCount == 0) 0 else absListView.getChildAt(0).top
                    mySwipeRefreshLayout.isEnabled = firstVisibleItem == 0 && topRowVerticalPosition >= 0
                }
            })
        }

        // Prevents gesture conflicts with ScrollView
        // <http://stackoverflow.com/a/26296897>

        fun scrollViewCompat(mySwipeRefreshLayout: MySwipeRefreshLayout, scrollView: ScrollView) {
            scrollView.viewTreeObserver.addOnScrollChangedListener { mySwipeRefreshLayout.isEnabled = scrollView.scrollY == 0 }
        }

    }

}
