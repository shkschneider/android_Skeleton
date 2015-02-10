package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
    public void setRefreshing(final boolean refreshing) {
        super.setRefreshing(refreshing);
    }

    @Override
    public boolean isRefreshing() {
        return super.isRefreshing();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (! isEnabled()) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    private int mTouchSlop;
    private float mX;

    // Prevents gesture conflicts with NavigationDrawer
    // <http://stackoverflow.com/a/23989911>
    @Override
    public boolean onInterceptTouchEvent(final MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = MotionEvent.obtain(motionEvent).getX();
                break ;
            case MotionEvent.ACTION_MOVE:
                final float x = motionEvent.getX();
                final float diff = Math.abs(x - mX);
                if (diff > mTouchSlop) {
                    return false;
                }
                break ;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    // Prevents gesture conflicts with AbsListView
    // <http://nlopez.io/swiperefreshlayout-with-listview-done-right/>
    // TODO tests
    public void absListViewCompat(@NonNull final AbsListView absListView) {
        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                // Ignore
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
                final int topRowVerticalPosition = ((absListView.getChildCount() == 0) ? 0 : absListView.getChildAt(0).getTop());
                setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }

        });
    }

    // Prevents gesture conflicts with ScrollView
    // <http://stackoverflow.com/a/26296897>
    // TODO tests
    public void scrollViewCompat(@NonNull final ScrollView scrollView) {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                setEnabled(scrollView.getScrollY() == 0);
            }
        });
    }

}
