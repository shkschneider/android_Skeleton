package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
        init();
    }

    private void init() {
        setEnabled(true);
        setColorSchemeResources(R.color.primaryColor);
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

    // <http://nlopez.io/swiperefreshlayout-with-listview-done-right/>
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

    // <http://stackoverflow.com/a/26296897>
    public void scrollViewCompat(@NonNull final ScrollView scrollView) {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                setEnabled(scrollView.getScrollY() == 0);
            }
        });
    }

}
