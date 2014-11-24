package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.util.AttributeSet;

import me.shkschneider.skeleton.R;

/**
 * PagerTabStrip that belongs just below the ActionBar.
 * Best in portrait mode.
 *
 * @see me.shkschneider.skeleton.ui.PagerTabStrip
 */
public class ActionBarPagerTabStrip extends PagerTabStrip {

    public ActionBarPagerTabStrip(final Context context) {
        this(context, null);
    }

    public ActionBarPagerTabStrip(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarPagerTabStrip(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(context, R.color.actionBarColor, R.color.actionBarForegroundColor, 8);
    }

}
