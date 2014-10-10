package me.sdk;

import android.content.Context;
import android.util.AttributeSet;

public class ListView extends android.widget.ListView {

    private Callback mCallback;

    public ListView(final Context context) {
        super(context);
        init();
    }

    public ListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_ALWAYS);
    }

    public void setCallback(final Callback callback) {
        mCallback = callback;
    }

    @Override
    protected boolean overScrollBy(final int deltaX, final int deltaY, final int scrollX, final int scrollY, final int scrollRangeX, final int scrollRangeY, final int maxOverScrollX, final int maxOverScrollY, final boolean isTouchEvent) {
        final boolean overscrolled = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        if (mCallback != null && isTouchEvent && deltaY < -25) {
            if (getFirstVisiblePosition() == 0) {
                mCallback.overscrolledTop();
            }
            else {
                mCallback.overscrolledBottom();
            }
        }
        return overscrolled;
    }

    public interface Callback {

        public void overscrolledTop();
        public void overscrolledBottom();

    }

}
