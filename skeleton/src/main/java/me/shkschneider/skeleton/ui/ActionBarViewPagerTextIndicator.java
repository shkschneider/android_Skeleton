package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.util.AttributeSet;

import me.shkschneider.skeleton.R;

/**
 * PagerTabStrip that shows text and belongs just below the ActionBar.
 * Best in portrait mode.
 *
 * @see me.shkschneider.skeleton.ui.ViewPagerIndicator
 */
public class ActionBarViewPagerTextIndicator extends ViewPagerTextIndicator {

    public ActionBarViewPagerTextIndicator(final Context context) {
        this(context, null);
    }

    public ActionBarViewPagerTextIndicator(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarViewPagerTextIndicator(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        final int viewPagerIndicatorHeight = getResources().getInteger(R.integer.viewPager_indicatorHeight);
        init(context, R.color.actionBarColor, R.color.actionBarForegroundColor, viewPagerIndicatorHeight);
    }

}
