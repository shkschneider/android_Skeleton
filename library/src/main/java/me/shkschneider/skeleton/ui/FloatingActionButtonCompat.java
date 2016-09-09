package me.shkschneider.skeleton.ui;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ListView;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ApplicationHelper;

// <https://github.com/makovkastar/FloatingActionButton>
public class FloatingActionButtonCompat {

    private static final int THRESHOLD = 5;

    public static void show(@NonNull final FloatingActionButton floatingActionButton, final boolean animate) {
        if (floatingActionButton.getVisibility() == View.VISIBLE) return;
        floatingActionButton.setVisibility(View.VISIBLE);
        if (! animate) return;
        floatingActionButton.startAnimation(AnimationUtils.loadAnimation(ApplicationHelper.context(), me.shkschneider.skeleton.R.anim.sk_scale_up));
    }

    public static void hide(@NonNull final FloatingActionButton floatingActionButton, final boolean animate) {
        if (floatingActionButton.getVisibility() == View.GONE) return;
        if (! animate) {
            floatingActionButton.setVisibility(View.GONE);
            return;
        }
        final Animation animation = AnimationUtils.loadAnimation(ApplicationHelper.context(), R.anim.sk_scale_down);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                // Ignore
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                floatingActionButton.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
                // Ignore
            }
        });
        floatingActionButton.startAnimation(animation);
    }

    public static void colors(@NonNull final FloatingActionButton floatingActionButton, @ColorInt final int normal, @ColorInt final int ripple) {
        // app:borderWidth="0dp"
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(normal));
        floatingActionButton.setRippleColor(ripple);
    }

    public static void absListView(@NonNull final FloatingActionButton floatingActionButton, @NonNull final ListView listView, final AbsListView.OnScrollListener onScrollListener) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int mLastScrollY;
            private int mPreviousFirstVisibleItem;

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
                if (totalItemCount == 0) {
                    if (onScrollListener != null) {
                        onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                    }
                    return;
                }
                if (firstVisibleItem == mPreviousFirstVisibleItem) {
                    final int newScrollY = getTopItemScrollY();
                    boolean isSignificantDelta = Math.abs(mLastScrollY - newScrollY) > THRESHOLD;
                    if (isSignificantDelta) {
                        if (mLastScrollY > newScrollY) {
                            floatingActionButton.hide();
                        }
                        else {
                            floatingActionButton.show();
                        }
                    }
                    mLastScrollY = newScrollY;
                }
                else {
                    if (firstVisibleItem > mPreviousFirstVisibleItem) {
                        floatingActionButton.hide();
                    }
                    else {
                        floatingActionButton.show();
                    }
                    mLastScrollY = getTopItemScrollY();
                    mPreviousFirstVisibleItem = firstVisibleItem;
                }
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }

            private int getTopItemScrollY() {
                final View topChild = listView.getChildAt(0);
                if (topChild == null) return 0;
                return topChild.getTop();
            }

            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

        });
    }

    public static void myScrollView(@NonNull final FloatingActionButton floatingActionButton, @NonNull final MyScrollView myScrollView) {
        myScrollView.setOnScrollViewListener(new MyScrollView.OnScrollViewListener() {

            private int mLastScrollY;

            @Override
            public void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
                boolean isSignificantDelta = Math.abs(t - mLastScrollY) > THRESHOLD;
                if (isSignificantDelta) {
                    if (t > mLastScrollY) {
                        floatingActionButton.hide();
                    } else {
                        floatingActionButton.show();
                    }
                }
                mLastScrollY = t;
            }

        });
    }

    // <http://stackoverflow.com/a/35981886>
    public static void recyclerView(@NonNull final FloatingActionButton floatingActionButton, @NonNull final RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    floatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                boolean isSignificantDelta = Math.abs(dy) > 5;
                if (/*dy < 0 || */(isSignificantDelta && dy > 0)) {
                    floatingActionButton.hide();
                }
            }

        });
    }

}
