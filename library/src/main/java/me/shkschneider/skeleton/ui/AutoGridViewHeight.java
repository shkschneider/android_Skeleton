package me.shkschneider.skeleton.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

import me.shkschneider.skeleton.helper.AndroidHelper;

// <http://stackoverflow.com/a/8483078>
public class AutoGridViewHeight extends GridView {

    private boolean mExpanded = false;

    public AutoGridViewHeight(final Context context) {
        super(context);
    }

    public AutoGridViewHeight(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoGridViewHeight(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressWarnings("unused")
    @TargetApi(AndroidHelper.API_21)
    public AutoGridViewHeight(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    @Override
    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        if (isExpanded()) {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            final int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
            final ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(final boolean expanded) {
        mExpanded = expanded;
    }

}
