package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.RunnableHelper;
import me.shkschneider.skeleton.java.StringHelper;

// <https://github.com/navasmdc/MaterialDesignLibrary>
// <https://github.com/nispok/snackbar>
public class SnackBar {

    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 3500;
    public static final int DURATION_INFINITE = -1;

    private static SnackBar SNACKBAR = null;

    private RelativeLayout relativeLayout;
    private Activity activity;
    private int lines;
    private int height;
    private int duration;
    private int backgroundColor;
    private int textColor;
    private int actionColor;
    private String text;
    private String action;
    private View attachedView;
    private View.OnClickListener onClickListener;
    private boolean showing;

    // Prevents direct initialization
    private SnackBar() {
        // Empty
    }

    public static SnackBar with(@NonNull final Activity activity, @NonNull final String text) {
        if (SNACKBAR == null) {
            SNACKBAR = new SnackBar();
        }
        SNACKBAR.lines = 1;
        SNACKBAR.duration = DURATION_SHORT;
        SNACKBAR.backgroundColor = ApplicationHelper.resources().getColor(R.color.snackBarBackgroundColor);
        SNACKBAR.textColor = ApplicationHelper.resources().getColor(R.color.snackBarForegroundColor);
        SNACKBAR.actionColor = ApplicationHelper.resources().getColor(R.color.accentColor);
        SNACKBAR.activity = activity;
        SNACKBAR.text = text;
        return SNACKBAR;
    }

    public static SnackBar with(@NonNull final Activity activity, @NonNull final String text, final int backgroundColor, final int textColor) {
        SNACKBAR = with(activity, text);
        SNACKBAR.backgroundColor = backgroundColor;
        SNACKBAR.textColor = textColor;
        return SNACKBAR;
    }

    public SnackBar duration(final int duration) {
        SNACKBAR.duration = duration;
        return this;
    }

    public SnackBar singleLine() {
        SNACKBAR.lines = 1;
        return this;
    }

    public SnackBar multiLine() {
        SNACKBAR.lines = 2;
        return this;
    }

    public SnackBar action(final String action, final View.OnClickListener listener, final int color) {
        SNACKBAR.action = action;
        SNACKBAR.actionColor = color;
        SNACKBAR.onClickListener = listener;
        return this;
    }

    public SnackBar action(final String action, final View.OnClickListener listener) {
        return action(action, listener, ApplicationHelper.resources().getColor(R.color.accentColor));
    }

//    public SnackBar watchForAbsListView(final AbsListView absListView) {
//        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
//                dismiss();
//            }
//
//            @Override
//            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
//                // Ignore
//            }
//        });
//        return this;
//    }

//    public SnackBar watchForRecyclerView(final RecyclerView recyclerView) {
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                dismiss();
//            }
//        });
//        return this;
//    }

    public SnackBar attachToView(final View view) {
        SNACKBAR.attachedView = view;
        return this;
    }

    private void build() {
        SNACKBAR.height = (int) ((SNACKBAR.lines == 1)
                ? ApplicationHelper.resources().getDimension(R.dimen.snackBarSingle)
                : ApplicationHelper.resources().getDimension(R.dimen.snackBarMulti));
        SNACKBAR.relativeLayout = new RelativeLayout(SNACKBAR.activity);
        final LayoutInflater layoutInflater = LayoutInflater.from(SNACKBAR.activity);
        final int layout = ((SNACKBAR.lines == 1) ? R.layout.snackbar1 : R.layout.snackbar2);
        final RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(layout, SNACKBAR.relativeLayout, true);
        relativeLayout.setBackgroundColor(SNACKBAR.backgroundColor);
        final TextView textView = (TextView) relativeLayout.findViewById(R.id.textview);
        textView.setTextColor(SNACKBAR.textColor);
        textView.setText(SNACKBAR.text);
        if (! StringHelper.nullOrEmpty(SNACKBAR.action)) {
            final TextView action = (TextView) relativeLayout.findViewById(R.id.action);
            action.setTextColor(SNACKBAR.actionColor);
            action.setText(SNACKBAR.action);
            action.setClickable(true);
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    dismiss();
                    if (SNACKBAR.onClickListener != null) {
                        SNACKBAR.onClickListener.onClick(view);
                    }
                }
            });
        }
        SNACKBAR.relativeLayout.setClickable(true); // avoid sub-layout clicking actions
        SNACKBAR.relativeLayout.requestLayout(); // invalidates this class' layout

        final ViewGroup viewGroup = (ViewGroup) SNACKBAR.activity.findViewById(android.R.id.content);
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SNACKBAR.height);
        layoutParams.gravity = Gravity.BOTTOM;
        relativeLayout.setLayoutParams(layoutParams);
        viewGroup.addView(relativeLayout, layoutParams);
    }

    public void show() {
        if (SNACKBAR.showing) {
            return ;
        }
        SNACKBAR.showing = true;
        build();

        SNACKBAR.relativeLayout.setVisibility(View.VISIBLE);
        SNACKBAR.relativeLayout.startAnimation(AnimationUtils.loadAnimation(SkeletonApplication.CONTEXT, R.anim.snackbar_show_animation));
        if (SNACKBAR.attachedView != null) {
            SNACKBAR.attachedView.animate().translationYBy(SNACKBAR.height * -1);
        }
        if (SNACKBAR.duration != DURATION_INFINITE) {
            RunnableHelper.delayRunnable(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, SNACKBAR.duration, TimeUnit.MILLISECONDS);
        }
    }

    public boolean showing() {
        return SNACKBAR.showing;
    }

    public void dismiss() {
        if (! SNACKBAR.showing) {
            return ;
        }

        final Animation animation = AnimationUtils.loadAnimation(SkeletonApplication.CONTEXT, R.anim.snackbar_hide_animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                // Ignore
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                SNACKBAR.relativeLayout.setVisibility(View.GONE);
                // Prevents multiple instances
                SNACKBAR.showing = false;
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
                // Ignore
            }
        });
        SNACKBAR.relativeLayout.startAnimation(animation);
        if (SNACKBAR.attachedView != null) {
            SNACKBAR.attachedView.animate().translationYBy(SNACKBAR.height);
        }
    }

    private void clear() {
        SNACKBAR.relativeLayout.clearAnimation();
        final ViewGroup parent = (ViewGroup) SNACKBAR.relativeLayout.getParent();
        if (parent != null) {
            parent.removeView(SNACKBAR.relativeLayout);
        }
    }

}
