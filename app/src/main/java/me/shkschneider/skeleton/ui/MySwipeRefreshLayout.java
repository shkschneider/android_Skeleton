package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    protected boolean mSwipes = true;

    public MySwipeRefreshLayout(final Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public void swipes(final boolean b) {
        mSwipes = b;
    }

    public boolean swipes() {
        return mSwipes;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (! mSwipes) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

}
