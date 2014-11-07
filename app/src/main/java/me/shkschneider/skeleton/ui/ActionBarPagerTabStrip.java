package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.util.AttributeSet;

import me.shkschneider.app.R;

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
