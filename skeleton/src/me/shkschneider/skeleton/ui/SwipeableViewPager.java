package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.jetbrains.annotations.NotNull;

public class SwipeableViewPager extends ViewPager {

    public SwipeableViewPager(@NotNull final Context context) {
        super(context);
    }

    public SwipeableViewPager(@NotNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return super.onTouchEvent(event);
    }

}