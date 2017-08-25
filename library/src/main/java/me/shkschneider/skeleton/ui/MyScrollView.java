package me.shkschneider.skeleton.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;

// <http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level/>
// <https://stackoverflow.com/a/26990539/603270>
// MyScrollView.setOnScrollViewListener()
public class MyScrollView extends ScrollView {

    protected static final float PARALLAX = 0.5F;

    private OnScrollViewListener mOnScrollViewListener;
    private View mView;
    private Float mParallax;

    public MyScrollView(final Context context) {
        super(context);
    }

    public MyScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    @TargetApi(AndroidHelper.API_21)
    public MyScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnScrollViewListener(final OnScrollViewListener onScrollViewListener) {
        mOnScrollViewListener = onScrollViewListener;
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mView != null) {
            mView.setTranslationY(getScrollY() * mParallax);
        }
        if (mOnScrollViewListener != null) {
            mOnScrollViewListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void parallax(@NonNull final View view) {
        mView = view;
        mParallax = PARALLAX;
    }

    public void parallax(@NonNull final View view, float parallax) {
        if (parallax <= 0) {
            LogHelper.warning("Parallax effect too low");
            parallax = PARALLAX;
        }
        if (parallax > 1) {
            LogHelper.warning("Parallax effect too high");
            parallax = PARALLAX;
        }
        mView = view;
        mParallax = parallax;
    }

    public static boolean canScroll(@NonNull final ScrollView scrollView) {
        final int childHeight = scrollView.getHeight();
        return (scrollView.getHeight() < childHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom());
    }

    public interface OnScrollViewListener {

        void onScrollChanged(int l, int t, int oldl, int oldt);

    }

}
