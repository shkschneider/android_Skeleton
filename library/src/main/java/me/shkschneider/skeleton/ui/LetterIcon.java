package me.shkschneider.skeleton.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.java.StringHelper;

// <https://github.com/IvBaranov/MaterialLetterIcon>
// TODO test
@Deprecated
public class LetterIcon extends View {

    private final static Rect RECT = new Rect();

    private Paint mShapePaint;
    private Paint mLetterPaint;
    private int mShapeColor = Color.BLACK;
    private String mLetter;
    private int mLetterColor = Color.WHITE;
    private int mLetterSize = 26; // R.dimen.textSizeBig

    public LetterIcon(final Context context) {
        this(context, null);
    }

    public LetterIcon(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIcon(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(AndroidHelper.API_21)
    public LetterIcon(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mShapePaint = new Paint();
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setAntiAlias(true);
        mLetterPaint = new Paint();
        mLetterPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(@NonNull final Canvas canvas) {
        final int w = getMeasuredWidth() / 2;
        final int h = getMeasuredHeight() / 2;
        final int radius = ((w > h) ? h : w);
        drawCircle(canvas, radius, w, h);
        if (mLetter != null) {
            drawLetter(canvas, w, h);
        }
    }

    private void drawCircle(@NonNull final Canvas canvas, final int radius, final int width, final int height) {
        mShapePaint.setColor(mShapeColor);
        canvas.drawCircle(width, height, radius, mShapePaint);
    }

    private void drawLetter(@NonNull final Canvas canvas, final float cx, final float cy) {
        mLetterPaint.setColor(mLetterColor);
        mLetterPaint.setTextSize(ScreenHelper.pixelsFromSp(mLetterSize));
        mLetterPaint.getTextBounds(mLetter, 0, mLetter.length(), RECT);
        canvas.drawText(mLetter, cx - RECT.exactCenterX(), cy - RECT.exactCenterY(), mLetterPaint);
    }

    public void setShapeColor(@ColorInt final int color) {
        this.mShapeColor = color;
        invalidate();
    }

    public void setLetter(@NonNull final String string) {
        this.mLetter = StringHelper.upper(string.replaceAll("\\s+", ""));
        invalidate();
    }

    public void setLetterColor(@ColorInt final int color) {
        this.mLetterColor = color;
        invalidate();
    }

    public void setLetterSize(@IntRange(from=0) final int size) {
        this.mLetterSize = size;
        invalidate();
    }

    public void setLetterTypeface(@NonNull final Typeface typeface) {
        this.mLetterPaint.setTypeface(typeface);
        invalidate();
    }

}
