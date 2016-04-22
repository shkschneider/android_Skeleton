package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ScrollView;

import me.shkschneider.skeleton.R;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    public MySwipeRefreshLayout(final Context context) {
        this(context, null);
    }

    public MySwipeRefreshLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        setEnabled(false);
        setColorSchemeResources(R.color.primaryColor);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public boolean isRefreshing() {
        return super.isRefreshing();
    }

    // Bugfix <https://code.google.com/p/android/issues/detail?id=77712>

    @Override
    public void setRefreshing(final boolean refreshing) {
        post(new Runnable() {
            @Override
            public void run() {
                MySwipeRefreshLayout.super.setRefreshing(refreshing);
            }
        });
    }

    // Bugfix <https://code.google.com/p/android/issues/detail?id=87789>

    private boolean mHandleTouch = true;

    @Override
    public boolean onTouchEvent(@NonNull final MotionEvent motionEvent) {
        final int action = MotionEventCompat.getActionMasked(motionEvent);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mHandleTouch = false;
                break ;
            default:
                if (mHandleTouch) {
                    return super.onTouchEvent(motionEvent);
                }
                mHandleTouch = onInterceptTouchEvent(motionEvent);
                switch (action) {
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mHandleTouch = true;
                        break ;
                }
                break ;
        }
        return true;
    }

    // Prevents gesture conflicts with NavigationDrawer
    // <http://stackoverflow.com/a/24453194>

    private int mTouchSlop;
    private float mX;
    private boolean mDeclined;

    @Override
    public boolean onInterceptTouchEvent(@NonNull final MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = MotionEvent.obtain(motionEvent).getX();
                mDeclined = false;
                break ;
            case MotionEvent.ACTION_MOVE:
                final float x = motionEvent.getX();
                final float diff = Math.abs(x - mX);
                if (mDeclined || diff > mTouchSlop) {
                    mDeclined = true;
                    return false;
                }
                break ;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    // Prevents gesture conflicts with RecyclerView
    // <http://stackoverflow.com/q/25178329>

    public static void recyclerViewCompat(@NonNull final MySwipeRefreshLayout mySwipeRefreshLayout, @NonNull final RecyclerView recyclerView, @NonNull final LinearLayoutManager linearLayoutManager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                final boolean idle = (newState == RecyclerView.SCROLL_STATE_IDLE);
                final boolean firstCompletelyVisibleItem = (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                mySwipeRefreshLayout.setEnabled(idle && firstCompletelyVisibleItem);
            }
        });
    }

    // Prevents gesture conflicts with AbsListView
    // <http://nlopez.io/swiperefreshlayout-with-listview-done-right/>

    public static void absListViewCompat(@NonNull final MySwipeRefreshLayout mySwipeRefreshLayout, @NonNull final AbsListView absListView) {
        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull final AbsListView view, final int scrollState) {
                // Ignore
            }

            @Override
            public void onScroll(@NonNull final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
                final int topRowVerticalPosition = ((absListView.getChildCount() == 0) ? 0 : absListView.getChildAt(0).getTop());
                mySwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }

        });
    }

    // Prevents gesture conflicts with ScrollView
    // <http://stackoverflow.com/a/26296897>

    public static void scrollViewCompat(@NonNull final MySwipeRefreshLayout mySwipeRefreshLayout, @NonNull final ScrollView scrollView) {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                mySwipeRefreshLayout.setEnabled(scrollView.getScrollY() == 0);
            }

        });
    }

}
