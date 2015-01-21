package me.shkschneider.skeleton.ui.viewpager;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;

// <https://github.com/ongakuer/CircleIndicator>
public class ViewPagerCircleIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;

    private ViewPager mViewpager;
    private ViewPager.OnPageChangeListener mViewPagerOnPageChangeListener;
    private int mIndicatorMargin;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mAnimatorResId;
    private int mIndicatorBackground;
    private int mCurrentPosition = 0;
    private AnimatorSet mAnimationOut;
    private AnimatorSet mAnimationIn;

    public ViewPagerCircleIndicator(final Context context) {
        this(context, null);
    }

    public ViewPagerCircleIndicator(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerCircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    @TargetApi(AndroidHelper.API_21)
    public ViewPagerCircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        mIndicatorMargin = ScreenHelper.pixelsFromDp(DEFAULT_INDICATOR_WIDTH);
        mIndicatorWidth = ScreenHelper.pixelsFromDp(DEFAULT_INDICATOR_WIDTH);
        mIndicatorHeight = ScreenHelper.pixelsFromDp(DEFAULT_INDICATOR_WIDTH);
        mAnimatorResId = R.animator.viewpager_circleindicator_scalealpha;
        mIndicatorBackground = R.drawable.viewpager_circleindicator;
        mCurrentPosition = 0;
        mAnimationOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mAnimationOut.setInterpolator(new LinearInterpolator());
        mAnimationIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mAnimationIn.setInterpolator(new ReverseInterpolator());
    }

    public void setViewPager(final ViewPager viewPager) {
        mViewpager = viewPager;
        createIndicators(viewPager);
        mViewpager.setOnPageChangeListener(this);
    }

    public void setIndicator(final int res) {
        mIndicatorBackground = res;
        createIndicators(mViewpager);
    }

    private void createIndicators(final ViewPager viewPager) {
        if (viewPager == null) {
            LogHelper.warning("ViewPager was NULL");
            return ;
        }

        removeAllViews();
        final int count = viewPager.getAdapter().getCount();
        if (count <= 0) {
            return ;
        }
        for (int i = 0; i < count; i++) {
            final View indicator = new View(getContext());
            indicator.setBackgroundResource(mIndicatorBackground);
            addView(indicator, mIndicatorWidth, mIndicatorHeight);
            final LayoutParams layoutParams = (LayoutParams) indicator.getLayoutParams();
            layoutParams.leftMargin = mIndicatorMargin;
            layoutParams.rightMargin = mIndicatorMargin;
            indicator.setLayoutParams(layoutParams);
            mAnimationOut.setTarget(indicator);
            mAnimationOut.start();
        }
        mAnimationOut.setTarget(getChildAt(mCurrentPosition));
        mAnimationOut.start();
    }

    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            LogHelper.warning("ViewPager was NULL");
            return ;
        }
        mViewPagerOnPageChangeListener = onPageChangeListener;
        mViewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(final int position) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageSelected(position);
        }
        mAnimationIn.setTarget(getChildAt(mCurrentPosition));
        mAnimationIn.start();
        mAnimationOut.setTarget(getChildAt(position));
        mAnimationOut.start();
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        if (mViewPagerOnPageChangeListener != null) {
            mViewPagerOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private class ReverseInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0F - value);
        }

    }

}
