package me.shkschneider.skeleton.ui.viewpager;

import android.content.Context;
import android.util.AttributeSet;

import me.shkschneider.skeleton.R;

public class ActionBarViewPagerIconIndicator extends ViewPagerIconIndicator {

    public ActionBarViewPagerIconIndicator(final Context context) {
        this(context, null);
    }

    public ActionBarViewPagerIconIndicator(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarViewPagerIconIndicator(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        final int viewPagerIndicatorHeight = getResources().getInteger(R.integer.viewPager_indicatorHeight);
        init(context, R.color.actionBarColor, R.color.actionBarForegroundColor, viewPagerIndicatorHeight);
        setFillViewport(true);
    }

}
