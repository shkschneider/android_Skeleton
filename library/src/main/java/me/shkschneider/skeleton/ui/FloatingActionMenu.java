package me.shkschneider.skeleton.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;

// <https://github.com/futuresimple/android-floating-action-button>
public class FloatingActionMenu extends ViewGroup {

    private static final int ANIMATION_DURATION = 300;
    private static final float COLLAPSED_PLUS_ROTATION = 0F;
    private static final float EXPANDED_PLUS_ROTATION = 90F + 45F;

    private int mAddButtonColor;
    private int mAddButtonSize;
    private int mButtonSpacing;
    private boolean mExpanded;

    private AnimatorSet mExpandAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AnimatorSet mCollapseAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AddFloatingActionButton mAddButton;
    private OnFloatingActionMenuListener mListener;
    private List<FloatingActionButton> mButtons;

    public FloatingActionMenu(final Context context) {
        this(context, null);
    }

    public FloatingActionMenu(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionMenu(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        mAddButtonColor = R.color.accentColor;
        mAddButtonSize = FloatingActionButton.SIZE_NORMAL;
        mButtonSpacing = (int) (getResources().getDimension(R.dimen.floatingActionButtonActionSpacing) - getResources().getDimension(R.dimen.floatingActionButtonShadowRadius) - getResources().getDimension(R.dimen.floatingActionButtonShadowOffset));
        mButtons = new ArrayList<>();
        createAddButton();
    }

    public void setButtonColor(@ColorRes final int color) {
        mAddButtonColor = color;
        updateAddButton();
    }

    public void setSize(final int size) {
        mAddButtonSize = size;
        updateAddButton();
    }

    public void addButton(@NonNull final FloatingActionButton floatingActionButton) {
        mButtons.add(floatingActionButton);
        addView(floatingActionButton, mButtons.size() - 1);
    }

    public void removeButton(@NonNull final FloatingActionButton floatingActionButton) {
        mButtons.remove(floatingActionButton);
        removeView(floatingActionButton);
    }

    public void clear() {
        for (final FloatingActionButton floatingActionButton : mButtons) {
            removeButton(floatingActionButton);
        }
    }

    private static class RotatingDrawable extends LayerDrawable {

        private float mRotation;

        public RotatingDrawable(final Drawable drawable) {
            super(new Drawable[] { drawable });
        }

        @SuppressWarnings("UnusedDeclaration")
        public float getRotation() {
            return mRotation;
        }

        @SuppressWarnings("UnusedDeclaration")
        public void setRotation(final float rotation) {
            mRotation = rotation;
            invalidateSelf();
        }

        @Override
        public void draw(final Canvas canvas) {
            canvas.save();
            canvas.rotate(mRotation, getBounds().centerX(), getBounds().centerY());
            super.draw(canvas);
            canvas.restore();
        }

    }

    private void updateAddButton() {
        if (mAddButton != null) {
            mAddButton.setColors(mAddButtonColor, mAddButtonColor);
            mAddButton.setSize(mAddButtonSize);
        }
    }

    private void createAddButton() {
        mAddButton = new AddFloatingActionButton(getContext()) {
            @Override
            protected Drawable getIconDrawable() {
                final RotatingDrawable rotatingDrawable = new RotatingDrawable(super.getIconDrawable());
                final OvershootInterpolator interpolator = new OvershootInterpolator();
                final ObjectAnimator collapseAnimator = ObjectAnimator.ofFloat(rotatingDrawable, "rotation", EXPANDED_PLUS_ROTATION, COLLAPSED_PLUS_ROTATION);
                final ObjectAnimator expandAnimator = ObjectAnimator.ofFloat(rotatingDrawable, "rotation", COLLAPSED_PLUS_ROTATION, EXPANDED_PLUS_ROTATION);
                collapseAnimator.setInterpolator(interpolator);
                expandAnimator.setInterpolator(interpolator);
                mExpandAnimation.play(expandAnimator);
                mCollapseAnimation.play(collapseAnimator);
                return rotatingDrawable;
            }
        };
        mAddButton.setSize(mAddButtonSize);
        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                toggle();
            }
        });
        addView(mAddButton, super.generateDefaultLayoutParams());
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            width = Math.max(width, child.getMeasuredWidth());
            height += child.getMeasuredHeight();
        }
        height += mButtonSpacing * (getChildCount() - 1);
        height = height * 12 / 10; // overshoots
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        final int addButtonY = (b - t - mAddButton.getMeasuredHeight());
        mAddButton.layout(0, addButtonY, mAddButton.getMeasuredWidth(), addButtonY + mAddButton.getMeasuredHeight());
        int nextY = (addButtonY - mButtonSpacing);
        for (int i = getChildCount() - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child == mAddButton) {
                continue ;
            }
            final int childX = (mAddButton.getMeasuredWidth() - child.getMeasuredWidth()) / 2;
            final int childY = (nextY - child.getMeasuredHeight());
            child.layout(childX, childY, childX + child.getMeasuredWidth(), childY + child.getMeasuredHeight());
            float collapsedTranslation = addButtonY - childY;
            float expandedTranslation = 0F;
            child.setTranslationY((mExpanded ? expandedTranslation : collapsedTranslation));
            child.setAlpha((mExpanded ? 1F : 0F));
            final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            layoutParams.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation);
            layoutParams.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation);
            layoutParams.setAnimationsTarget(child);
            nextY = (childY - mButtonSpacing);
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(super.generateDefaultLayoutParams());
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(final AttributeSet attrs) {
        return new LayoutParams(super.generateLayoutParams(attrs));
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(final ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(super.generateLayoutParams(layoutParams));
    }

    @Override
    protected boolean checkLayoutParams(final ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams);
    }

    private static Interpolator INTERPOLAR_EXPAND = new OvershootInterpolator();
    private static Interpolator INTERPOLAR_COLLAPSE = new DecelerateInterpolator(3F);
    private static Interpolator INTERPOLAR_EXPAND_ALPHA = new DecelerateInterpolator();

    private class LayoutParams extends ViewGroup.LayoutParams {

        private ObjectAnimator mExpandDir = new ObjectAnimator();
        private ObjectAnimator mExpandAlpha = new ObjectAnimator();
        private ObjectAnimator mCollapseDir = new ObjectAnimator();
        private ObjectAnimator mCollapseAlpha = new ObjectAnimator();
        private boolean animationsSetToPlay;

        public LayoutParams(final ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            mExpandDir.setInterpolator(INTERPOLAR_EXPAND);
            mExpandAlpha.setInterpolator(INTERPOLAR_EXPAND_ALPHA);
            mCollapseDir.setInterpolator(INTERPOLAR_COLLAPSE);
            mCollapseAlpha.setInterpolator(INTERPOLAR_COLLAPSE);
            mCollapseAlpha.setProperty(View.ALPHA);
            mCollapseAlpha.setFloatValues(1F, 0F);
            mExpandAlpha.setProperty(View.ALPHA);
            mExpandAlpha.setFloatValues(0F, 1F);
            mCollapseDir.setProperty(View.TRANSLATION_Y);
            mExpandDir.setProperty(View.TRANSLATION_Y);
        }

        public void setAnimationsTarget(final View view) {
            mCollapseAlpha.setTarget(view);
            mCollapseDir.setTarget(view);
            mExpandAlpha.setTarget(view);
            mExpandDir.setTarget(view);
            if (! animationsSetToPlay) {
                mCollapseAnimation.play(mCollapseAlpha);
                mCollapseAnimation.play(mCollapseDir);
                mExpandAnimation.play(mExpandAlpha);
                mExpandAnimation.play(mExpandDir);
                animationsSetToPlay = true;
            }
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        bringChildToFront(mAddButton);
    }

    public void collapse() {
        if (mExpanded) {
            mExpanded = false;
            mCollapseAnimation.start();
            mExpandAnimation.cancel();
            if (mListener != null) {
                mListener.onMenuCollapsed();
            }
        }
    }

    public void toggle() {
        if (mExpanded) {
            collapse();
        }
        else {
            expand();
        }
    }

    public void expand() {
        if (! mExpanded) {
            mExpanded = true;
            mCollapseAnimation.cancel();
            mExpandAnimation.start();
            if (mListener != null) {
                mListener.onMenuExpanded();
            }
        }
    }

    private class AddFloatingActionButton extends FloatingActionButton {

        public AddFloatingActionButton(final Context context) {
            this(context, null);
        }

        public AddFloatingActionButton(final Context context, final AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public AddFloatingActionButton(final Context context, final AttributeSet attrs, final int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected Drawable getIconDrawable() {
            final float iconSize = getResources().getDimension(R.dimen.floatingActionButtonIconSize);
            final float iconHalfSize = iconSize / 2F;
            final float plusSize = getResources().getDimension(R.dimen.floatingActionButtonPlusIconSize);
            final float plusHalfStroke = getResources().getDimension(R.dimen.floatingActionButtonPlusIconStroke) / 2F;
            final float plusOffset = (iconSize - plusSize) / 2F;
            final Shape shape = new Shape() {
                @Override
                public void draw(final Canvas canvas, final Paint paint) {
                    canvas.drawRect(plusOffset, iconHalfSize - plusHalfStroke, iconSize - plusOffset, iconHalfSize + plusHalfStroke, paint);
                    canvas.drawRect(iconHalfSize - plusHalfStroke, plusOffset, iconHalfSize + plusHalfStroke, iconSize - plusOffset, paint);
                }
            };
            final ShapeDrawable drawable = new ShapeDrawable(shape);
            final Paint paint = drawable.getPaint();
            paint.setColor(getResources().getColor(R.color.sk_android_white));
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            return drawable;
        }

    }

    public void setOnFloatingActionsMenuUpdateListener(final OnFloatingActionMenuListener listener) {
        mListener = listener;
    }

    public interface OnFloatingActionMenuListener {

        void onMenuExpanded();
        void onMenuCollapsed();

    }

}
