package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

// <http://stackoverflow.com/a/12283909>
public class AutoImageView extends ImageView {

    public AutoImageView(final Context context) {
        super(context);
    }

    public AutoImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return ;
        }
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = (int) Math.ceil((float) width * (float) drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
        setMeasuredDimension(width, height);
    }

}
