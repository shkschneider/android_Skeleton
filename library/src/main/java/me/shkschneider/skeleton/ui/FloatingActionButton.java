package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
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
                        createFillDrawable(strokeWidth),
                        createOuterStrokeDrawable(strokeWidth),
                        getIconDrawable()
                });
        final int iconOffset = (int) (mCircleSize - getResources().getDimension(R.dimen.floatingActionButtonIconSize)) / 2;
        final int circleInsetHorizontal = (int) (mShadowRadius);
        final int circleInsetTop = (int) (mShadowRadius - mShadowOffset);
        final int circleInsetBottom = (int) (mShadowRadius + mShadowOffset);
        layerDrawable.setLayerInset(1, circleInsetHorizontal, circleInsetTop, circleInsetHorizontal, circleInsetBottom);
        layerDrawable.setLayerInset(2, (int) (circleInsetHorizontal - halfStrokeWidth), (int) (circleInsetTop - halfStrokeWidth), (int) (circleInsetHorizontal - halfStrokeWidth), (int) (circleInsetBottom - halfStrokeWidth));
        layerDrawable.setLayerInset(3, circleInsetHorizontal + iconOffset, circleInsetTop + iconOffset, circleInsetHorizontal + iconOffset, circleInsetBottom + iconOffset);
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

    private StateListDrawable createFillDrawable(final float strokeWidth) {
        final StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[] { android.R.attr.state_pressed }, createCircleDrawable(mColorPressed, strokeWidth));
        drawable.addState(new int[] { }, createCircleDrawable(mColorNormal, strokeWidth));
        return drawable;
    }

    private Drawable createCircleDrawable(final int color, float strokeWidth) {
        final int alpha = Color.alpha(color);
        final int opaqueColor = opaque(color);
        final ShapeDrawable fillDrawable = new ShapeDrawable(new OvalShape());
        final Paint paint = fillDrawable.getPaint();
        paint.setAntiAlias(true);
        paint.setColor(opaqueColor);
        final Drawable[] layers = {
                fillDrawable,
                createInnerStrokesDrawable(opaqueColor, strokeWidth)
        };
        final LayerDrawable drawable = ((alpha == 255) ? new LayerDrawable(layers) : new TranslucentLayerDrawable(alpha, layers));
        final int halfStrokeWidth = (int) (strokeWidth / 2F);
        drawable.setLayerInset(1, halfStrokeWidth, halfStrokeWidth, halfStrokeWidth, halfStrokeWidth);
        return drawable;
    }

    private static class TranslucentLayerDrawable extends LayerDrawable {

        private final int mAlpha;

        public TranslucentLayerDrawable(final int alpha, final Drawable... layers) {
            super(layers);
            mAlpha = alpha;
        }

        @Override
        public void draw(final Canvas canvas) {
            final Rect bounds = getBounds();
            canvas.saveLayerAlpha(bounds.left, bounds.top, bounds.right, bounds.bottom, mAlpha, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            canvas.restore();
        }

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

    private int darkenColor(final int argb) {
        return adjustColorBrightness(argb, 0.9F);
    }

    private int lightenColor(final int argb) {
        return adjustColorBrightness(argb, 1.1F);
    }

    private int adjustColorBrightness(final int argb, final float factor) {
        final float[] hsv = new float[3];
        Color.colorToHSV(argb, hsv);
        hsv[2] = Math.min(hsv[2] * factor, 1F);
        return Color.HSVToColor(Color.alpha(argb), hsv);
    }

    private int halfTransparent(final int argb) {
        return Color.argb(Color.alpha(argb) / 2, Color.red(argb), Color.green(argb), Color.blue(argb));
    }

    private int opaque(final int argb) {
        return Color.rgb(Color.red(argb), Color.green(argb), Color.blue(argb));
    }

    private Drawable createInnerStrokesDrawable(final int color, final float strokeWidth) {
        final ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        final int bottomStrokeColor = darkenColor(color);
        final int bottomStrokeColorHalfTransparent = halfTransparent(bottomStrokeColor);
        final int topStrokeColor = lightenColor(color);
        final int topStrokeColorHalfTransparent = halfTransparent(topStrokeColor);
        final Paint paint = shapeDrawable.getPaint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Style.STROKE);
        shapeDrawable.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(final int width, final int height) {
                return new LinearGradient(width / 2, 0, width / 2, height,
                        new int[] { topStrokeColor, topStrokeColorHalfTransparent, color, bottomStrokeColorHalfTransparent, bottomStrokeColor },
                        new float[] { 0F, 0.2F, 0.5F, 0.8F, 1F },
                        TileMode.CLAMP);
            }
        });
        return shapeDrawable;
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