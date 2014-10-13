package me.sdk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

public class MyListView extends ListView {

    private Callback mCallback = null;
    private int mFirstVisiblePosition = -1;
    private int mDelta = 0;

    public MyListView(final Context context) {
        super(context);
        init();
    }

    public MyListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyListView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private int mLastFirstVisibleItem;
    private boolean mIsScrollingUp;

    private void init() {
        setOverScrollMode(OVER_SCROLL_ALWAYS);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                final int currentFirstVisibleItem = getFirstVisiblePosition();
                if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                    mIsScrollingUp = false;
                }
                else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                    mIsScrollingUp = true;
                }
                else {
                    // Stays the same
                }
                mLastFirstVisibleItem = currentFirstVisibleItem;

                if (mCallback != null) {
                    if (mIsScrollingUp) {
                        mCallback.scrollUp();
                    }
                    else {
                        mCallback.scrollDown();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ;
            }
        });
    }

    public void setCallback(final Callback callback) {
        mCallback = callback;
    }

    @Override
    public boolean onTouchEvent(@NotNull final MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            mCallback.overscroll(0);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void setOnScrollListener(final OnScrollListener onScrollListener) {
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > mFirstVisiblePosition) {
                    if (mCallback != null) {
                        mCallback.scrollDown();
                    }
                }
                else {
                    if (mCallback != null) {
                        mCallback.scrollUp();
                    }
                }
                mDelta = 0;
                mFirstVisiblePosition = firstVisibleItem;

                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    @Override
    protected boolean overScrollBy(final int deltaX, final int deltaY, final int scrollX, final int scrollY, final int scrollRangeX, final int scrollRangeY, final int maxOverScrollX, final int maxOverScrollY, final boolean isTouchEvent) {
        final boolean overscrolled = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        if (mCallback != null && isTouchEvent) {
            final boolean top = (getFirstVisiblePosition() == 0);
            if (deltaY < -25) {
                if (top) {
                    mCallback.overscrollTop();
                }
                else {
                    mCallback.overscrollBottom();
                }
            }
            else {
                mDelta += deltaY * -1;
                if (mDelta >= 100) {
                    mDelta = 100;
                    if (top) {
                        mCallback.overscrollTop();
                    }
                    else {
                        mCallback.overscrollBottom();
                    }
                }
                else {
                    mCallback.overscroll(mDelta);
                }
            }
        }
        return overscrolled;
    }

    public interface Callback {

        public void scrollUp();
        public void scrollDown();
        public void overscroll(final int n);
        public void overscrollTop();
        public void overscrollBottom();

    }

}
