package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.shkschneider.skeleton.R;

/**
 * PagerTabStrip that show line and belongs just below the ActionBar.
 * Best in landscape mode.
 *
 * @see me.shkschneider.skeleton.ui.ViewPagerIndicator
 */
public class ActionBarViewPagerLineIndicator extends ViewPagerIndicator {

    public ActionBarViewPagerLineIndicator(final Context context) {
        this(context, null);
    }

    public ActionBarViewPagerLineIndicator(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarViewPagerLineIndicator(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        final int viewPagerIndicatorHeight = getResources().getInteger(R.integer.viewPager_indicatorHeight);
        init(context, R.color.actionBarColor, R.color.actionBarForegroundColor, viewPagerIndicatorHeight);
    }

    private View createDefaultStrip(final Context context) {
        final View view = new View(context);
        final int padding = (int) (getResources().getInteger(R.integer.viewPager_padding) * getResources().getDisplayMetrics().density);
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
