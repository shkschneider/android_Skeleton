package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

// <http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level/>
public class MyScrollView extends ScrollView {

    private OnScrollViewListener mOnScrollViewListener;

    public MyScrollView(final Context context) {
        super(context);
    }

    public MyScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollViewListener(final OnScrollViewListener onScrollViewListener) {
        mOnScrollViewListener = onScrollViewListener;
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollViewListener != null) {
            mOnScrollViewListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public interface OnScrollViewListener {

        public void onScrollChanged(int l, int t, int oldl, int oldt);

    }

}
