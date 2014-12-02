package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    protected boolean mRefreshable = true;

    public MySwipeRefreshLayout(final Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRefreshable(final boolean refreshable) {
        mRefreshable = refreshable;
    }

    public boolean isRefreshable() {
        return mRefreshable;
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
        if (! mRefreshable) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

}
