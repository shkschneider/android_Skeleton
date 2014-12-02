package me.shkschneider.skeleton.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import me.shkschneider.skeleton.R;

// <https://github.com/futuresimple/android-floating-action-button>
public class FloatingActionMenu extends ViewGroup {

    public static final int EXPAND_UP = 0;
    public static final int EXPAND_DOWN = 1;
    // FIXME public static final int EXPAND_LEFT = 2;
    // FIXME public static final int EXPAND_RIGHT = 3;

    private static final int ANIMATION_DURATION = 300;
    private static final float COLLAPSED_PLUS_ROTATION = 0F;
    private static final float EXPANDED_PLUS_ROTATION = 90F + 45F;

    private int mAddButtonPlusColor;
    private int mAddButtonColor;
    private int mExpandDirection;
    private int mButtonSpacing;
    private boolean mExpanded;

    private AnimatorSet mExpandAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AnimatorSet mCollapseAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AddFloatingActionButton mAddButton;
    private RotatingDrawable mRotatingDrawable;

    public FloatingActionMenu(final Context context) {
        this(context, null);
    }

    public FloatingActionMenu(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatingActionMenu(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        mAddButtonPlusColor = getResources().getColor(R.color.white);
        mAddButtonColor = getResources().getColor(R.color.accentColor);
        mExpandDirection = EXPAND_UP;
        mButtonSpacing = (int) (getResources().getDimension(R.dimen.floatingActionButtonActionSpacing) - getResources().getDimension(R.dimen.floatingActionButtonShadowRadius) - getResources().getDimension(R.dimen.floatingActionButtonShadowOffset));
        createAddButton();
    }

    public void setButtonColor(final int color) {
        mAddButtonColor = color;
        updateAddButton();
    }

    public void setPlusColor(final int color) {
        mAddButtonPlusColor = color;
        updateAddButton();
    }

    public void setExpandDirection(final int direction) {
        mExpandDirection = direction;
        if (mAddButton != null) {
            removeView(mAddButton);
        }
        createAddButton();
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
            mAddButton.mPlusColor = mAddButtonPlusColor;
            mAddButton.mColorNormal = mAddButtonColor;
            mAddButton.mColorPressed = mAddButtonColor;
            mAddButton.updateBackground();
        }
    }

    private void createAddButton() {
        mAddButton = new AddFloatingActionButton(getContext()) {
            @Override
            protected void updateBackground() {
                mPlusColor = mAddButtonPlusColor;
                mColorNormal = mAddButtonColor;
                mColorPressed = mAddButtonColor;
                super.updateBackground();
            }

            @Override
            protected Drawable getIconDrawable() {
                final RotatingDrawable rotatingDrawable = new RotatingDrawable(super.getIconDrawable());
                mRotatingDrawable = rotatingDrawable;
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
        mAddButton.setId(R.id.fab_expand_menu_button);
        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
            switch (mExpandDirection) {
                case EXPAND_UP:
                case EXPAND_DOWN:
                    width = Math.max(width, child.getMeasuredWidth());
                    height += child.getMeasuredHeight();
                    break ;
//                case EXPAND_LEFT:
//                case EXPAND_RIGHT:
//                    width += child.getMeasuredWidth();
//                    height = Math.max(height, child.getMeasuredHeight());
            }
        }
        switch (mExpandDirection) {
            case EXPAND_UP:
            case EXPAND_DOWN:
                height += mButtonSpacing * (getChildCount() - 1);
                height = height * 12 / 10; // overshoots
                break ;
//            case EXPAND_LEFT:
//            case EXPAND_RIGHT:
//                width += mButtonSpacing * (getChildCount() - 1);
//                width = width * 12 / 10; // overshoots
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        switch (mExpandDirection) {
            case EXPAND_UP:
            case EXPAND_DOWN:
                final boolean expandUp = mExpandDirection == EXPAND_UP;
                final int addButtonY = expandUp ? b - t - mAddButton.getMeasuredHeight() : 0;
                mAddButton.layout(0, addButtonY, mAddButton.getMeasuredWidth(), addButtonY + mAddButton.getMeasuredHeight());
                int nextY = (expandUp ? (addButtonY - mButtonSpacing) : (addButtonY + mAddButton.getMeasuredHeight() + mButtonSpacing));
                for (int i = getChildCount() - 1; i >= 0; i--) {
                    final View child = getChildAt(i);
                    if (child == mAddButton) {
                        continue ;
                    }
                    final int childX = (mAddButton.getMeasuredWidth() - child.getMeasuredWidth()) / 2;
                    final int childY = (expandUp ? (nextY - child.getMeasuredHeight()) : nextY);
                    child.layout(childX, childY, childX + child.getMeasuredWidth(), childY + child.getMeasuredHeight());
                    float collapsedTranslation = addButtonY - childY;
                    float expandedTranslation = 0F;
                    child.setTranslationY((mExpanded ? expandedTranslation : collapsedTranslation));
                    child.setAlpha((mExpanded ? 1F : 0f));
                    final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                    layoutParams.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation);
                    layoutParams.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation);
                    layoutParams.setAnimationsTarget(child);
                    nextY = (expandUp ? (childY - mButtonSpacing) : (childY + child.getMeasuredHeight() + mButtonSpacing));
                }
                break ;
//            case EXPAND_LEFT:
//            case EXPAND_RIGHT:
//                final boolean expandLeft = mExpandDirection == EXPAND_LEFT;
//                final int addButtonX = (expandLeft ? (r - l - mAddButton.getMeasuredWidth()) : 0);
//                mAddButton.layout(addButtonX, 0, addButtonX + mAddButton.getMeasuredWidth(), mAddButton.getMeasuredHeight());
//                int nextX = (expandLeft ? (addButtonX - mButtonSpacing) : (addButtonX + mAddButton.getMeasuredWidth() + mButtonSpacing));
//                for (int i = getChildCount() - 1; i >= 0; i--) {
//                    final View child = getChildAt(i);
//                    if (child == mAddButton) {
//                        continue ;
//                    }
//                    final int childX = (expandLeft ? (nextX - child.getMeasuredWidth()) : nextX);
//                    final int childY = (mAddButton.getMeasuredHeight() - child.getMeasuredHeight()) / 2;
//                    child.layout(childX, childY, childX + child.getMeasuredWidth(), childY + child.getMeasuredHeight());
//                    final float collapsedTranslation = addButtonX - childX;
//                    final float expandedTranslation = 0F;
//                    child.setTranslationX((mExpanded ? expandedTranslation : collapsedTranslation));
//                    child.setAlpha((mExpanded ? 1F : 0F));
//                    final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
//                    layoutParams.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation);
//                    layoutParams.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation);
//                    layoutParams.setAnimationsTarget(child);
//                    nextX = (expandLeft ? (childX - mButtonSpacing) : (childX + child.getMeasuredWidth()) + mButtonSpacing);
//                }
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
            switch (mExpandDirection) {
                case EXPAND_UP:
                case EXPAND_DOWN:
                    mCollapseDir.setProperty(View.TRANSLATION_Y);
                    mExpandDir.setProperty(View.TRANSLATION_Y);
                    break ;
//                case EXPAND_LEFT:
//                case EXPAND_RIGHT:
//                    mCollapseDir.setProperty(View.TRANSLATION_X);
//                    mExpandDir.setProperty(View.TRANSLATION_X);
            }
            mExpandAnimation.play(mExpandAlpha);
            mExpandAnimation.play(mExpandDir);
            mCollapseAnimation.play(mCollapseAlpha);
            mCollapseAnimation.play(mCollapseDir);
        }

        public void setAnimationsTarget(final View view) {
            mCollapseAlpha.setTarget(view);
            mCollapseDir.setTarget(view);
            mExpandAlpha.setTarget(view);
            mExpandDir.setTarget(view);
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
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final SavedState savedState = new SavedState(superState);
        savedState.mExpanded = mExpanded;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(final Parcelable state) {
        if (state instanceof SavedState) {
            final SavedState savedState = (SavedState) state;
            mExpanded = savedState.mExpanded;
            if (mRotatingDrawable != null) {
                mRotatingDrawable.setRotation(mExpanded ? EXPANDED_PLUS_ROTATION : COLLAPSED_PLUS_ROTATION);
            }
            super.onRestoreInstanceState(savedState.getSuperState());
        }
        else {
            super.onRestoreInstanceState(state);
        }
    }

    public static class SavedState extends BaseSavedState {

        public boolean mExpanded;

        public SavedState(final Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(final Parcel parcel) {
            super(parcel);
            mExpanded = (parcel.readInt() == 1);
        }

        @Override
        public void writeToParcel(@NonNull final Parcel parcel, final int flags) {
            super.writeToParcel(parcel, flags);
            parcel.writeInt((mExpanded ? 1 : 0));
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(final Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override
            public SavedState[] newArray(final int size) {
                return new SavedState[size];
            }
        };

    }

    private class AddFloatingActionButton extends FloatingActionButton {

        protected int mPlusColor;

        public AddFloatingActionButton(final Context context) {
            this(context, null);
        }

        public AddFloatingActionButton(final Context context, final AttributeSet attrs) {
            super(context, attrs);
        }

        public AddFloatingActionButton(final Context context, final AttributeSet attrs, final int defStyle) {
            super(context, attrs, defStyle);
        }

        public void setPlusColor(final int color) {
            mPlusColor = color;
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
            paint.setColor(mPlusColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            return drawable;
        }

    }


}
