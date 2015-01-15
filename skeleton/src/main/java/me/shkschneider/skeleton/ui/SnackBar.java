package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.RunnableHelper;
import me.shkschneider.skeleton.java.StringHelper;

// <https://github.com/navasmdc/MaterialDesignLibrary>
// <https://github.com/nispok/snackbar>
public class SnackBar extends RelativeLayout {

    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 3500;
    public static final int DURATION_INFINITE = -1;

    private static SnackBar mSnackBar = null;

    private Activity mActivity;
    private int mLines = 1;
    private int mDuration = DURATION_SHORT;
    private int mBackgroundColor = getResources().getColor(R.color.snackBarBackgroundColor);
    private int mTextColor = getResources().getColor(R.color.snackBarForegroundColor);
    private int mActionColor = getResources().getColor(R.color.accentColor);
    private String mText;
    private String mAction;
    private View mAttachedView;
    private OnClickListener mOnClickListener;

    @SuppressWarnings("deprecation")
    @SuppressLint("deprecation")
    @Deprecated
    public SnackBar(final Context context) {
        this(context, null);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("deprecation")
    @Deprecated
    public SnackBar(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Deprecated
    public SnackBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    @TargetApi(AndroidHelper.API_21)
    @Deprecated
    public SnackBar(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("deprecation")
    public static SnackBar with(@NonNull final Activity activity, @NonNull final String text) {
        final SnackBar snackBar = new SnackBar(activity);
        snackBar.mActivity = activity;
        snackBar.mText = text;
        return snackBar;
    }

    public static SnackBar with(@NonNull final Activity activity, @NonNull final String text, final int backgroundColor, final int textColor) {
        final SnackBar snackBar = SnackBar.with(activity, text);
        snackBar.mBackgroundColor = backgroundColor;
        snackBar.mTextColor = textColor;
        return snackBar;
    }

    public SnackBar duration(final int duration) {
        mDuration = duration;
        return this;
    }

    public SnackBar singleLine() {
        mLines = 1;
        return this;
    }

    public SnackBar multiLine() {
        mLines = 2;
        return this;
    }

    public SnackBar action(final String action, final OnClickListener listener, final int color) {
        mAction = action;
        mActionColor = color;
        mOnClickListener = listener;
        return this;
    }

    public SnackBar action(final String action, final OnClickListener listener) {
        return action(action, listener, getResources().getColor(R.color.accentColor));
    }

    public SnackBar watchForAbsListView(final AbsListView absListView) {
        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                dismiss();
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
                // Ignore
            }
        });
        return this;
    }

    public SnackBar watchForRecyclerView(final RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                dismiss();
            }
        });
        return this;
    }

    public SnackBar attachToView(final View view) {
        mAttachedView = view;
        return this;
    }

    public int height() {
        return  (int) ((mLines == 1)
                ? getResources().getDimension(R.dimen.snackBarSingle)
                : getResources().getDimension(R.dimen.snackBarMulti));
    }

    private void build() {
        final LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        final int layout = ((mLines == 1) ? R.layout.snackbar1 : R.layout.snackbar2);
        final RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(layout, this, true);
        relativeLayout.setBackgroundColor(mBackgroundColor);
        final TextView textView = (TextView) relativeLayout.findViewById(R.id.textview);
        textView.setTextColor(mTextColor);
        textView.setText(mText);
        if (! StringHelper.nullOrEmpty(mAction)) {
            final TextView action = (TextView) relativeLayout.findViewById(R.id.action);
            action.setTextColor(mActionColor);
            action.setText(mAction);
            action.setClickable(true);
            action.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View view) {
                    dismiss();
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(view);
                    }
                }
            });
        }
        setClickable(true); // avoid sub-layout clicking actions
        requestLayout(); // invalidates this class' layout

        final ViewGroup viewGroup = (ViewGroup) mActivity.findViewById(android.R.id.content);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height());
        layoutParams.gravity = Gravity.BOTTOM;
        relativeLayout.setLayoutParams(layoutParams);
        viewGroup.addView(relativeLayout, layoutParams);
    }

    public void show() {
        if (mSnackBar != null) {
            return ;
        }
        mSnackBar = this;

        build();
        setVisibility(View.VISIBLE);
        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.snackbar_show_animation));
        if (mAttachedView != null) {
            mAttachedView.animate().translationYBy(height() * -1);
        }
        if (mDuration != DURATION_INFINITE) {
            RunnableHelper.delayRunnable(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, mDuration, TimeUnit.MILLISECONDS);
        }
    }

    public void dismiss() {
        if (mSnackBar == null) {
            return ;
        }

        final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.snackbar_hide_animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                // Ignore
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                setVisibility(View.GONE);
                // Prevents multiple instances
                mSnackBar = null;
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
                // Ignore
            }
        });
        startAnimation(animation);
        if (mAttachedView != null) {
            mAttachedView.animate().translationYBy(height());
        }
    }

    private void clear() {
        clearAnimation();
        final ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
    }

}
