package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageButton;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;

// <https://github.com/futuresimple/android-floating-action-button>
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
        this(context, attrs, 0);
    }

    public FloatingActionButton(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @TargetApi(AndroidHelper.API_21)
    public FloatingActionButton(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        final float strokeWidth = getResources().getDimension(R.dimen.floatingActionButtonStrokeWidth);
        final float halfStrokeWidth = strokeWidth / 2F;
        final LayerDrawable layerDrawable = new LayerDrawable(
                new Drawable[] {
                        getResources().getDrawable((mSize == SIZE_NORMAL) ? R.drawable.fab_bg_normal : R.drawable.fab_bg_mini),
                        createFillDrawable(),
                        createOuterStrokeDrawable(strokeWidth),
                        createInnerStrokesDrawable(strokeWidth),
                        getIconDrawable()
                });
        final int iconOffset = (int) (mCircleSize - getResources().getDimension(R.dimen.floatingActionButtonIconSize)) / 2;
        final int circleInsetHorizontal = (int) (mShadowRadius);
        final int circleInsetTop = (int) (mShadowRadius - mShadowOffset);
        final int circleInsetBottom = (int) (mShadowRadius + mShadowOffset);
        layerDrawable.setLayerInset(1, circleInsetHorizontal, circleInsetTop, circleInsetHorizontal, circleInsetBottom);
        layerDrawable.setLayerInset(2, (int) (circleInsetHorizontal - halfStrokeWidth), (int) (circleInsetTop - halfStrokeWidth), (int) (circleInsetHorizontal - halfStrokeWidth), (int) (circleInsetBottom - halfStrokeWidth));
        layerDrawable.setLayerInset(3, (int) (circleInsetHorizontal - halfStrokeWidth), (int) (circleInsetTop - halfStrokeWidth), (int) (circleInsetHorizontal - halfStrokeWidth), (int) (circleInsetBottom - halfStrokeWidth));
        layerDrawable.setLayerInset(4, circleInsetHorizontal + iconOffset, circleInsetTop + iconOffset, circleInsetHorizontal + iconOffset, circleInsetBottom + iconOffset);
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

    private StateListDrawable createFillDrawable() {
        final StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, createCircleDrawable(mColorPressed));
        drawable.addState(new int[]{}, createCircleDrawable(mColorNormal));
        return drawable;
    }

    private Drawable createCircleDrawable(final int color) {
        final ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        final Paint paint = shapeDrawable.getPaint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        return shapeDrawable;
    }

    private Drawable createOuterStrokeDrawable(float strokeWidth) {
        final ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        final Paint paint = shapeDrawable.getPaint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setAlpha(opacityToAlpha(0.02F));
        return shapeDrawable;
    }

    private int opacityToAlpha(final float opacity) {
        return (int) (opacity * 255F);
    }

    private Drawable createInnerStrokesDrawable(float strokeWidth) {
        final ShapeDrawable innerBottom = new ShapeDrawable(new OvalShape());
        final Paint bottomPaint = innerBottom.getPaint();
        bottomPaint.setAntiAlias(true);
        bottomPaint.setStrokeWidth(strokeWidth);
        bottomPaint.setStyle(Style.STROKE);
        bottomPaint.setAlpha(opacityToAlpha(0.04F));
        innerBottom.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(width / 2, 0, width / 2, height,
                        new int[] { Color.TRANSPARENT, HALF_TRANSPARENT_BLACK, Color.BLACK },
                        new float[] { 0F, 0.8F, 1F },
                        TileMode.CLAMP);
            }
        });
        final ShapeDrawable innerTop = new ShapeDrawable(new OvalShape());
        final Paint topPaint = innerTop.getPaint();
        topPaint.setAntiAlias(true);
        topPaint.setStrokeWidth(strokeWidth);
        topPaint.setStyle(Style.STROKE);
        topPaint.setAlpha(opacityToAlpha(0.8F));
        innerTop.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(width / 2, 0, width / 2, height,
                        new int[] { Color.WHITE, HALF_TRANSPARENT_WHITE, Color.TRANSPARENT },
                        new float[] { 0F, 0.2F, 1F },
                        TileMode.CLAMP);
            }
        });
        return new LayerDrawable(new Drawable[] { innerBottom, innerTop });
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