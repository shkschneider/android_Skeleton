package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageButton;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;

/**
 * FloatingActionButton that works inside a FloatingActionMenu and as a standalone Button.
 * Design introduced with Material Design.
 *
 * @see <https://github.com/futuresimple/android-floating-action-button>
 */
public class FloatingActionButton extends ImageButton {

    public static final int SIZE_NORMAL = 0;
    public static final int SIZE_MINI = 1;

    private static final int HALF_TRANSPARENT_WHITE = Color.argb(128, 255, 255, 255);
    private static final int HALF_TRANSPARENT_BLACK = Color.argb(128, 0, 0, 0);

    protected int mColorNormal;
    protected int mColorPressed;
    protected int mIcon;
    protected int mSize;

    private float mCircleSize;
    private float mShadowRadius;
    private float mShadowOffset;
    private int mDrawableSize;

    public FloatingActionButton(final Context context) {
        this(context, null);
    }

    public FloatingActionButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatingActionButton(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        mColorNormal = getResources().getColor(R.color.accentColor);
        mColorPressed = getResources().getColor(R.color.accentColor);
        mIcon = 0;
        mSize = SIZE_NORMAL;
        mCircleSize = getResources().getDimension(R.dimen.floatingActionButtonNormal);
        mShadowRadius = getResources().getDimension(R.dimen.floatingActionButtonShadowRadius);
        mShadowOffset = getResources().getDimension(R.dimen.floatingActionButtonShadowOffset);
        mDrawableSize = (int) (mCircleSize + (mShadowRadius * 2));
        updateBackground();
    }

    public void setColors(@ColorRes final int colorNormal, @ColorRes final int colorPressed) {
        mColorNormal = getResources().getColor(colorNormal);
        mColorPressed = getResources().getColor(colorPressed);
        updateBackground();
    }

    public void setIcon(@DrawableRes final int icon) {
        mIcon = icon;
        updateBackground();
    }

    public void setSize(final int size) {
        mSize = size;
        mCircleSize = ((mSize == SIZE_NORMAL) ? getResources().getDimension(R.dimen.floatingActionButtonNormal) : getResources().getDimension(R.dimen.floatingActionButtonMini));
        mDrawableSize = (int) (mCircleSize + (mShadowRadius * 2));
        updateBackground();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mDrawableSize, mDrawableSize);
    }

    protected void updateBackground() {
        final float circleLeft = mShadowRadius;
        final float circleTop = mShadowRadius - mShadowOffset;
        final RectF circleRect = new RectF(circleLeft, circleTop, circleLeft + mCircleSize, circleTop + mCircleSize);
        final LayerDrawable layerDrawable = new LayerDrawable(
                new Drawable[] {
                        getResources().getDrawable((mSize == SIZE_NORMAL) ? R.drawable.fab_bg_normal : R.drawable.fab_bg_mini),
                        createFillDrawable(circleRect),
                        createStrokesDrawable(circleRect),
                        getIconDrawable()
                });
        final float iconOffset = (mCircleSize - getResources().getDimension(R.dimen.floatingActionButtonIconSize)) / 2F;
        final int iconInsetHorizontal = (int) (mShadowRadius + iconOffset);
        final int iconInsetTop = (int) (circleTop + iconOffset);
        final int iconInsetBottom = (int) (mShadowRadius + mShadowOffset + iconOffset);
        layerDrawable.setLayerInset(3, iconInsetHorizontal, iconInsetTop, iconInsetHorizontal, iconInsetBottom);
        setBackgroundCompat(layerDrawable);
    }

    protected Drawable getIconDrawable() {
        if (mIcon != 0) {
            try {
                return getResources().getDrawable(mIcon);
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
        }
        return new ColorDrawable(Color.TRANSPARENT);
    }

    private StateListDrawable createFillDrawable(final RectF circleRect) {
        final StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[] { android.R.attr.state_pressed }, createCircleDrawable(circleRect, mColorPressed));
        drawable.addState(new int[] { }, createCircleDrawable(circleRect, mColorNormal));
        return drawable;
    }

    private Drawable createCircleDrawable(final RectF circleRect, final int color) {
        final Bitmap bitmap = Bitmap.createBitmap(mDrawableSize, mDrawableSize, Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawOval(circleRect, paint);
        return new BitmapDrawable(getResources(), bitmap);
    }

    private int opacityToAlpha(final float opacity) {
        return (int) (opacity * 255F);
    }

    private Drawable createStrokesDrawable(final RectF circleRect) {
        final Bitmap bitmap = Bitmap.createBitmap(mDrawableSize, mDrawableSize, Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final float strokeWidth = getResources().getDimension(R.dimen.floatingActionButtonStrokeWidth);
        final float halfStrokeWidth = strokeWidth / 2F;
        final RectF outerStrokeRect = new RectF(
                circleRect.left - halfStrokeWidth,
                circleRect.top - halfStrokeWidth,
                circleRect.right + halfStrokeWidth,
                circleRect.bottom + halfStrokeWidth
        );
        final RectF innerStrokeRect = new RectF(
                circleRect.left + halfStrokeWidth,
                circleRect.top + halfStrokeWidth,
                circleRect.right - halfStrokeWidth,
                circleRect.bottom - halfStrokeWidth
        );
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Style.STROKE);
        // outer
        paint.setColor(Color.BLACK);
        paint.setAlpha(opacityToAlpha(0.02F));
        canvas.drawOval(outerStrokeRect, paint);
        // inner bottom
        paint.setShader(new LinearGradient(innerStrokeRect.centerX(), innerStrokeRect.top, innerStrokeRect.centerX(), innerStrokeRect.bottom,
                new int[] { Color.TRANSPARENT, HALF_TRANSPARENT_BLACK, Color.BLACK },
                new float[] { 0F, 0.8F, 1F },
                TileMode.CLAMP
        ));
        paint.setAlpha(opacityToAlpha(0.04f));
        canvas.drawOval(innerStrokeRect, paint);
        // inner top
        paint.setShader(new LinearGradient(innerStrokeRect.centerX(), innerStrokeRect.top, innerStrokeRect.centerX(), innerStrokeRect.bottom,
                new int[] { Color.WHITE, HALF_TRANSPARENT_WHITE, Color.TRANSPARENT },
                new float[] { 0F, 0.2F, 1F },
                TileMode.CLAMP
        ));
        paint.setAlpha(opacityToAlpha(0.8F));
        canvas.drawOval(innerStrokeRect, paint);
        return new BitmapDrawable(getResources(), bitmap);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void setBackgroundCompat(final Drawable drawable) {
        if (AndroidHelper.api() >= AndroidHelper.API_16) {
            setBackground(drawable);
        }
        else {
            setBackgroundDrawable(drawable);
        }
    }

}