package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.util.AttributeSet;

import me.shkschneider.skeleton.R;

/**
 * PagerTabStrip that shows icons and belongs just below the ActionBar.
 * Best in portrait mode.
 *
 * @see me.shkschneider.skeleton.ui.ViewPagerIndicator
 */
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
    }

}
