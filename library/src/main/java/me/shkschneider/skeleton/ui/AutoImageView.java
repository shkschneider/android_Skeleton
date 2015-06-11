package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

// <http://stackoverflow.com/a/12283909>
public class AutoImageView extends ImageView {

    public AutoImageView(final Context context) {
        this(context, null);
    }

    public AutoImageView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAdjustViewBounds(true);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return ;
        }

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) Math.ceil((float) width * (float) drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
        if (height > width) {
            height = (int) Math.ceil((float) width * (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight());
        }
        setMeasuredDimension(width, height);
    }

}
