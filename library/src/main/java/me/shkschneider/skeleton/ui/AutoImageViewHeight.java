package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

// <http://stackoverflow.com/a/12283909>
public class AutoImageViewHeight extends AppCompatImageView {

    private float mRatio = 1.0F;

    public AutoImageViewHeight(final Context context) {
        super(context);
    }

    public AutoImageViewHeight(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoImageViewHeight(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getRatio() {
        return mRatio;
    }

    public void setRatio(final float ratio) {
        mRatio = ratio;
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = (int) Math.ceil((float) height * (float) drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
        setMeasuredDimension((int) (mRatio * width), (int) (mRatio * height));
    }

}
