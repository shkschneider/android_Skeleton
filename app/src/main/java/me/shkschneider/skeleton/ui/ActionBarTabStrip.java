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

public class ActionBarTabStrip extends HorizontalScrollView {

    private int mTitleOffset;
    private int mLayoutId;
    private int mTextViewId;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final ActionBarTabStripCell mTabStripCell;

    public ActionBarTabStrip(final Context context) {
        this(context, null);
    }

    public ActionBarTabStrip(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarTabStrip(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        mTitleOffset = (int) (24 * getResources().getDisplayMetrics().density);
        mTabStripCell = new ActionBarTabStripCell(context);
        addView(mTabStripCell, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setBackgroundColor(getResources().getColor(R.color.primaryColor));
    }

    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    public void setCustomTabView(final int layoutResId, final int textViewId) {
        mLayoutId = layoutResId;
        mTextViewId = textViewId;
    }

    public void setViewPager(final ViewPager viewPager) {
        mTabStripCell.removeAllViews();
        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    protected TextView createDefaultTabView(final Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            textView.setAllCaps(true);
        }
        final int padding = (int) (16 * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextColor(getResources().getColor(R.color.actionBarForegroundColor));
        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();
        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;
            if (mLayoutId != 0) {
                tabView = LayoutInflater.from(getContext()).inflate(mLayoutId, mTabStripCell, false);
                tabTitleView = (TextView) tabView.findViewById(mTextViewId);
            }
            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
            }
            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }
            if (tabView != null) {
                tabView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F));
                tabView.setOnClickListener(tabClickListener);
            }
            if (tabTitleView != null) {
                tabTitleView.setText(adapter.getPageTitle(i));
            }
            mTabStripCell.addView(tabView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(final int tabIndex, final int positionOffset) {
        final int tabStripChildCount = mTabStripCell.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return ;
        }
        final View selectedChild = mTabStripCell.getChildAt(tabIndex);
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
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            final int tabStripChildCount = mTabStripCell.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return ;
            }
            mTabStripCell.onViewPagerPageChanged(position, positionOffset);
            final View selectedTitle = mTabStripCell.getChildAt(position);
            final int extraOffset = ((selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0);
            scrollToTab(position, extraOffset);
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mScrollState = state;
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(final int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStripCell.onViewPagerPageChanged(position, 0F);
                scrollToTab(position, 0);
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View view) {
            for (int i = 0; i < mTabStripCell.getChildCount(); i++) {
                if (view == mTabStripCell.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return ;
                }
            }
        }

    }

}
