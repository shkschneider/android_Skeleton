package me.shkschneider.skeleton.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import org.jetbrains.annotations.NotNull;

public class FloatingActionButton extends View {

    final static OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
    final static AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();

    private Paint mButtonPaint;
    private Paint mDrawablePaint;
    private Bitmap mBitmap;
    private boolean mVisible = true;

    public FloatingActionButton(final Context context) {
        super(context);
        init(Color.WHITE);
    }

    public void setFloatingActionButtonColor(final int FloatingActionButtonColor) {
        init(FloatingActionButtonColor);
    }

    public void setFloatingActionButtonDrawable(final Drawable FloatingActionButtonDrawable) {
        mBitmap = ((BitmapDrawable) FloatingActionButtonDrawable).getBitmap();
        invalidate();
    }

    public void init(final int FloatingActionButtonColor) {
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mButtonPaint.setColor(FloatingActionButtonColor);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setShadowLayer(10.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        mDrawablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        setClickable(true);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (float) (getWidth() / 2.6), mButtonPaint);
        canvas.drawBitmap(mBitmap, (getWidth() - mBitmap.getWidth()) / 2, (getHeight() - mBitmap.getHeight()) / 2, mDrawablePaint);
    }

    @Override
    public boolean onTouchEvent(@NotNull final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
          setAlpha(1.0f);
        }
        else if (event.getAction() == MotionEvent.ACTION_DOWN) {
          setAlpha(0.6f);
        }
        return super.onTouchEvent(event);
    }

    public void hideFloatingActionButton() {
        if (mVisible) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1, 0);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1, 0);
            AnimatorSet animSetXY = new AnimatorSet();
            animSetXY.playTogether(scaleX, scaleY);
            animSetXY.setInterpolator(accelerateInterpolator);
            animSetXY.setDuration(100);
            animSetXY.start();
            mVisible = false;
        }
    }

    public void showFloatingActionButton() {
        if (! mVisible) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0, 1);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0, 1);
            AnimatorSet animSetXY = new AnimatorSet();
            animSetXY.playTogether(scaleX, scaleY);
            animSetXY.setInterpolator(overshootInterpolator);
            animSetXY.setDuration(200);
            animSetXY.start();
            mVisible = true;
        }
    }

    public boolean isVisible() {
        return mVisible;
    }

    static public class Builder {

        private static final int DEFAULT_SIZE = 72;

        private Activity mActivity;
        private FrameLayout.LayoutParams mLayoutParams;
        private int mGravity = Gravity.BOTTOM | Gravity.RIGHT; // default bottom right
        private Drawable mDrawable;
        private int mColor = Color.WHITE;
        private int mSize = 0;
        private float mScale = 0;

        public Builder(final Activity activity) {
            mActivity = activity;
            mScale = mActivity.getResources().getDisplayMetrics().density;
            mSize = convertToPixels(DEFAULT_SIZE, mScale);
            mLayoutParams = new FrameLayout.LayoutParams(mSize, mSize);
            mLayoutParams.gravity = mGravity;
        }

        /**
        * Sets the gravity for the FAB
        */
        public Builder withGravity(final int gravity) {
            mGravity = gravity;
            return this;
        }

        /**
        * Sets the margins for the FAB in dp
        */
        public Builder withMargins(final int left, final int top, final int right, final int bottom) {
            mLayoutParams.setMargins(convertToPixels(left, mScale),
                  convertToPixels(top, mScale),
                  convertToPixels(right, mScale),
                  convertToPixels(bottom, mScale));
            return this;
        }

        /**
        * Sets the FAB drawable
        */
        public Builder withDrawable(final Drawable drawable) {
            mDrawable = drawable;
            return this;
        }

        /**
        * Sets the FAB color
        */
        public Builder withButtonColor(final int color) {
            mColor = color;
            return this;
        }

        /**
        * Sets the FAB size in dp
        */
        public Builder withButtonSize(final int dp) {
            final int size = convertToPixels(dp, mScale);
            mLayoutParams = new FrameLayout.LayoutParams(size, size);
            return this;
        }

        public FloatingActionButton create() {
            final FloatingActionButton button = new FloatingActionButton(mActivity);
            button.setFloatingActionButtonColor(this.mColor);
            button.setFloatingActionButtonDrawable(this.mDrawable);
            mLayoutParams.gravity = this.mGravity;
            ViewGroup root = (ViewGroup) mActivity.findViewById(android.R.id.content);
            root.addView(button, mLayoutParams);
            return button;
        }

        // TODO use ScreenHelper
        // The calculation (value * mScale + 0.5f) is a widely used to convert to dps to pixel units
        // based on density mScale
        // see developer.android.com (Supporting Multiple Screen Sizes)
        private int convertToPixels(final int dp, final float scale) {
            return (int) (dp * scale + 0.5f) ;
        }

    }

}
