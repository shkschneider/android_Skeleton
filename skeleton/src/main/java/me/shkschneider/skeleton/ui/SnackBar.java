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

import me.shkschneider.skeleton.java.Executor;
import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.java.StringHelper;

// <https://github.com/navasmdc/MaterialDesignLibrary>
public class SnackBar extends RelativeLayout {

    private static final int DURATION_SHORT = 2000;
    private static final int DURATION_LONG = 3500;

    private Activity mActivity;
    private int mLines = 1;
    private String mText;
    private String mAction;
    private OnClickListener mOnClickListener;
    private boolean mShowing = false;

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

    public SnackBar singleLine() {
        mLines = 1;
        return this;
    }

    public SnackBar multiLine() {
        mLines = 2;
        return this;
    }

    public SnackBar action(final String action, final OnClickListener listener) {
        mAction = action;
        mOnClickListener = listener;
        return this;
    }

    // TODO test
    public SnackBar attachToAbsListView(final AbsListView absListView) {
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

    // TODO test
    public SnackBar attachToRecyclerView(final RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                dismiss();
            }
        });
        return this;
    }

    private void build() {
        final LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        final int layout = ((mLines == 1) ? R.layout.snackbar1 : R.layout.snackbar2);
        final RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(layout, this, true);
        final TextView textView = (TextView) relativeLayout.findViewById(R.id.textview);
        textView.setText(mText);
        if (! StringHelper.nullOrEmpty(mAction)) {
            final TextView action = (TextView) relativeLayout.findViewById(R.id.action);
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
        final int height = ScreenHelper.pixelsFromDp(((mLines == 1) ? 48 : 80));
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        layoutParams.gravity = Gravity.BOTTOM;
        relativeLayout.setLayoutParams(layoutParams);
        viewGroup.addView(relativeLayout, layoutParams);
    }

    public void show() {
        if (mShowing) {
            return ;
        }
        mShowing = true;
        build();
        setVisibility(View.VISIBLE);
        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.snackbar_show_animation));
        final int duration = ((! StringHelper.nullOrEmpty(mAction)) ? DURATION_LONG : DURATION_SHORT);
        Executor.delayRunnable(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, duration, TimeUnit.MILLISECONDS);
    }

    public void dismiss() {
        if (! mShowing) {
            return ;
        }
        mShowing = false;
        final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.snackbar_hide_animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                // Ignore
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
                // Ignore
            }
        });
        startAnimation(animation);
    }

    private void clear() {
        clearAnimation();
        final ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
    }

}
