package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.jetbrains.annotations.NotNull;

public class NonSwipeableViewPager extends ViewPager {

    public NonSwipeableViewPager(@NotNull final Context context) {
        super(context);
    }

    public NonSwipeableViewPager(@NotNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return false;
    }

}
