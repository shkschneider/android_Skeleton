package me.shkschneider.skeleton.ui;

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.shkschneider.app.R;

// TODO cleanup
public class SlidingTabLayout extends HorizontalScrollView {

    public interface TabColorizer {

        int getIndicatorColor(int position);
        int getDividerColor(int position);

    }

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

    private int mTitleOffset;
    private int mTabViewLayoutId;
    private boolean mTabViewFullWidth;
    private int mTabViewTextViewId;
    private int mTextViewColor;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final SlidingTabStrip mTabStrip;

    public SlidingTabLayout(Context context) {
        this(context, null);
        init();
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);
        mTabStrip = new SlidingTabStrip(context);
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        init();
    }

    private void init() {
        mTabViewFullWidth = false;
        setCustomTabColorizer(new TabColorizer() {
            @Override
            public int getIndicatorColor(final int position) {
                return getResources().getColor(R.color.actionBarForegroundColor);
            }

            @Override
            public int getDividerColor(final int position) {
                return getResources().getColor(R.color.transparent);
            }
        });
        mTextViewColor = getResources().getColor(R.color.actionBarForegroundColor);
    }

    public void setTabViewFullWidth(boolean fullWidth) {
        mTabViewFullWidth = fullWidth;
    }

    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    public void setDividerColors(int... colors) {
        mTabStrip.setDividerColors(colors);
    }

    public void setTextColor(int color) {
        mTextViewColor = color;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();
        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            textView.setAllCaps(true);
        }
        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextColor(mTextViewColor);
        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();
        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;
            if (mTabViewLayoutId != 0) {
                tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip, false);
                tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
            }
            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
            }
            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }
            if (tabView != null) {
                if (mTabViewFullWidth) {
                    tabView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F));
                }
                tabView.setOnClickListener(tabClickListener);
            }
            if (tabTitleView != null) {
                tabTitleView.setText(adapter.getPageTitle(i));
            }
            mTabStrip.addView(tabView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }
        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;
            if (tabIndex > 0 || positionOffset > 0) {
                targetScrollX -= mTitleOffset;
            }
            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return ;
            }
            mTabStrip.onViewPagerPageChanged(position, positionOffset);
            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = ((selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0);
            scrollToTab(position, extraOffset);
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return ;
                }
            }
        }

    }

}
