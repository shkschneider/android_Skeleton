package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;

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

    // <http://nlopez.io/swiperefreshlayout-with-listview-done-right/>
    public static void listViewCompat(@NonNull final MySwipeRefreshLayout mySwipeRefreshLayout, @NonNull final AbsListView absListView) {
        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                // Ignore
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
                final int topRowVerticalPosition = ((absListView.getChildCount() == 0) ? 0 : absListView.getChildAt(0).getTop());
                mySwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
    }

}
