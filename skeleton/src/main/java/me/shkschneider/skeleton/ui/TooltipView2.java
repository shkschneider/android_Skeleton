package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import me.shkschneider.skeleton.helper.AndroidHelper;

// <https://github.com/venmo/tooltip-view>
public class TooltipView2 extends TextView {

    private static TooltipView2 INSTANCE = null;

    public static TooltipView2 with(@NonNull final View view) {
        INSTANCE = new TooltipView2(view.getContext());
        INSTANCE.setView(view);
        return INSTANCE;
    }

    public TooltipView2 color(final int color) {
        mColor = color;
        invalidate();
        return this;
    }

    private static final int ARROW_HEIGHT = 8;
    private static final int ARROW_WIDTH = 16;
    private static final int CORNER_RADIUS = 4;

    private View mView;
    private int mColor = Color.TRANSPARENT;
    private Paint mPaint;
    private Path mPath;

    public TooltipView2(final Context context) {
        this(context, null);
    }

    public TooltipView2(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TooltipView2(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    @TargetApi(AndroidHelper.API_21)
    public TooltipView2(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setView(final View view) {
        mView = view;
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + ARROW_HEIGHT);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        mPath = null;
        mPaint = null;
    }

    @Override
    protected void onDraw(@NonNull final Canvas canvas) {
        if (mPath == null || mPaint == null) {
            init(canvas);
        }
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);
    }

    private void init(final Canvas canvas) {
        mPath = new Path();
        final RectF rectF = new RectF(canvas.getClipBounds());
        rectF.bottom -= ARROW_HEIGHT;
        mPath.addRoundRect(rectF, CORNER_RADIUS, CORNER_RADIUS, Direction.CW);
        float middle = rectF.width() / 2;
        if (mView != null) {
            middle += mView.getX() + mView.getWidth() / 2 - getX() - getWidth() / 2;
        }
        mPath.moveTo(middle, getHeight());
        final int arrowDx = ARROW_WIDTH / 2;
        mPath.lineTo(middle - arrowDx, rectF.bottom);
        mPath.lineTo(middle + arrowDx, rectF.bottom);
        mPath.close();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
    }

}
