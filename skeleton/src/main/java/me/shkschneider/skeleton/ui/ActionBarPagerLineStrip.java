package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.shkschneider.skeleton.R;

/**
 * PagerTabStrip that belongs just below the ActionBar.
 * Best in landscape mode.
 *
 * @see me.shkschneider.skeleton.ui.PagerTabStrip
 */
public class ActionBarPagerLineStrip extends PagerTabStrip {

    public ActionBarPagerLineStrip(final Context context) {
        this(context, null);
    }

    public ActionBarPagerLineStrip(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarPagerLineStrip(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(context, R.color.actionBarColor, R.color.actionBarForegroundColor, 8);
    }

    private View createDefaultStrip(final Context context) {
        final View view = new View(context);
        final int padding = (int) (16 * getResources().getDisplayMetrics().density);
        view.setPadding(padding, padding, padding, padding);
        return view;
    }

    @Override
    protected void populateStrip() {
        final PagerAdapter pagerAdapter = mViewPager.getAdapter();
        final View.OnClickListener onClickListener = new TabClickListener();
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            final View tabView = createDefaultStrip(getContext());
            tabView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F));
            tabView.setOnClickListener(onClickListener);
            mTabStripCell.addView(tabView);
        }
    }

}
