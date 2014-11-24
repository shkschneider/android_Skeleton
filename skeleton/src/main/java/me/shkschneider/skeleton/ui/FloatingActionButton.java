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

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ScreenHelper;

public class FloatingActionButton extends View {

    private Paint mButtonPaint;
    private Paint mDrawablePaint;
    private Bitmap mBitmap;
    private boolean mVisible = true;

    public FloatingActionButton(@NotNull final Context context) {
        super(context);
        init(getResources().getColor(R.color.accentColor));
    }

    private void init(final int floatingActionButtonColor) {
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mButtonPaint.setColor(floatingActionButtonColor);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setShadowLayer(10.0F, 0.0F, 3.5F, Color.argb(100, 0, 0, 0));
        mDrawablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        invalidate();
    }

    protected void setFloatingActionButtonColor(final int FloatingActionButtonColor) {
        init(FloatingActionButtonColor);
    }

    protected void setFloatingActionButtonDrawable(final Drawable FloatingActionButtonDrawable) {
        mBitmap = ((BitmapDrawable) FloatingActionButtonDrawable).getBitmap();
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
          setAlpha(1.0F);
        }
        else if (event.getAction() == MotionEvent.ACTION_DOWN) {
          setAlpha(0.6F);
        }
        return super.onTouchEvent(event);
    }

    public void hide() {
        if (mVisible) {
            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1, 0);
            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setInterpolator(new AccelerateInterpolator());
            animatorSet.setDuration(100);
            animatorSet.start();
            mVisible = false;
        }
    }

    public void show() {
        if (! mVisible) {
            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0, 1);
            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0, 1);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setInterpolator(new OvershootInterpolator());
            animatorSet.setDuration(200);
            animatorSet.start();
            mVisible = true;
        }
    }

    public boolean isVisible() {
        return mVisible;
    }

    static public class Builder {

        private static final int DEFAULT_SIZE = 72;
        private static final int DEFAULT_MARGIN = 16;
        private static final int DEFAULT_GRAVITY = Gravity.BOTTOM | Gravity.RIGHT;

        private Activity mActivity;
        private FrameLayout.LayoutParams mLayoutParams;
        private int mGravity = Gravity.BOTTOM | Gravity.RIGHT;
        private Drawable mDrawable;
        private int mColor = Color.WHITE;
        private OnClickListener mOnClickListener;

        public Builder(final Activity activity) {
            mActivity = activity;
            withButtonSize(DEFAULT_SIZE);
            withGravity(DEFAULT_GRAVITY);
            withMargins(DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN);
            // withDrawable()
            withButtonColor(mActivity.getResources().getColor(R.color.accentColor));
        }

        private Builder withButtonSize(final int dp) {
            final int size = ScreenHelper.pixelsFromDp(dp);
            mLayoutParams = new FrameLayout.LayoutParams(size, size);
            return this;
        }

        private Builder withGravity(final int gravity) {
            mGravity = gravity;
            return this;
        }

        private Builder withMargins(final int left, final int top, final int right, final int bottom) {
            mLayoutParams.setMargins(ScreenHelper.pixelsFromDp(left),
                    ScreenHelper.pixelsFromDp(top),
                    ScreenHelper.pixelsFromDp(right),
                    ScreenHelper.pixelsFromDp(bottom));
            return this;
        }

        public Builder withDrawable(final Drawable drawable) {
            mDrawable = drawable;
            return this;
        }

        public Builder withButtonColor(final int color) {
            mColor = color;
            return this;
        }

        public Builder withOnClickListener(final OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
            return this;
        }

        public FloatingActionButton create() {
            final FloatingActionButton floatingActionButton = new FloatingActionButton(mActivity);
            floatingActionButton.setFloatingActionButtonColor(mColor);
            floatingActionButton.setFloatingActionButtonDrawable(mDrawable);
            mLayoutParams.gravity = mGravity;
            final ViewGroup viewGroup = (ViewGroup) mActivity.findViewById(android.R.id.content);
            floatingActionButton.setOnClickListener(mOnClickListener);
            viewGroup.addView(floatingActionButton, mLayoutParams);
            return floatingActionButton;
        }

    }

}
