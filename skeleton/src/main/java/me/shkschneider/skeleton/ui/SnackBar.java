package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.helper.StringHelper;

/**
 * SnackBar is an enhanced and contextual Toast that can have a Button inside it.
 * Introduced with Material Design.
 *
 * @see <https://github.com/navasmdc/MaterialDesignLibrary>
 */
public class SnackBar extends RelativeLayout {

    private Activity mActivity;
    private int mLines = 1;
    private String mText;
    private String mAction;
    private OnClickListener mOnClickListener;
    private boolean mShowing = false;

    @Deprecated
    public SnackBar(final Context context) {
        this(context, null);
    }

    @Deprecated
    public SnackBar(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Deprecated
    public SnackBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Deprecated
    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    @TargetApi(AndroidHelper.API_21)
    public SnackBar(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("deprecation")
    public static SnackBar with(@NotNull final Activity activity, @NotNull final String text) {
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
                hide();
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
                hide();
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
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(view);
                    }
                    hide();
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
        setVisibility(View.VISIBLE);
        build();
        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.snackbar_show_animation));
        mShowing = true;
    }

    public void hide() {
        if (! mShowing) {
            return ;
        }
        startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.snackbar_hide_animation));
        mShowing = false;
        setVisibility(View.GONE);
    }

    private void clear() {
        clearAnimation();
        final ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
    }

}
