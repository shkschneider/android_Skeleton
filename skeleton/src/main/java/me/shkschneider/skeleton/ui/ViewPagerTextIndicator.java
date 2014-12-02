package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.util.AttributeSet;

import me.shkschneider.skeleton.R;

public class ViewPagerTextIndicator extends ViewPagerIndicator {

    public ViewPagerTextIndicator(final Context context) {
        this(context, null);
    }

    public ViewPagerTextIndicator(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerTextIndicator(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        final int viewPagerIndicatorHeight = getResources().getInteger(R.integer.viewPager_indicatorHeight);
        init(context, R.color.contentBackgroundColor, R.color.primaryColor, viewPagerIndicatorHeight);
    }

}
