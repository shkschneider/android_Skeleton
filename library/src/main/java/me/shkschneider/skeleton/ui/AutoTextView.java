package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

// <http://jeffreysambells.com/2010/04/04/android-textview-with-auto-sized-content>
public class AutoTextView extends AppCompatTextView {

    public AutoTextView(final Context context) {
        this(context, null);
    }

    public AutoTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull final Canvas canvas) {
        super.onDraw(canvas);
        sizeToFit();
    }

    public void sizeToFit() {
        final int height = getHeight();
        final int lines = getLineCount();
        final Rect rect = new Rect();
        // final int y1 = getLineBounds(0, rect);
        final int y2 = getLineBounds(lines - 1, rect);
        final float size = getTextSize();
        if (y2 > height && size >= 8.0F) {
            setTextSize(size - 2.0F);
            sizeToFit();
        }
    }

}
