package me.shkschneider.skeleton.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewAnimationUtils;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;

public class AnimationHelper {

    public static void revealOn(@NonNull final View view) {
        if (AndroidHelper.api() < AndroidHelper.API_21) {
            view.setVisibility(View.VISIBLE);
            return;
        }
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(final View v,
                                       final int left, final int top, final int right, final int bottom,
                                       final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {
                view.removeOnLayoutChangeListener(this);
                revealOn21(view);
            }
        });
    }

    @TargetApi(AndroidHelper.API_21)
    private static void revealOn21(@NonNull final View view) {
        final int width = view.getMeasuredWidth() / 2;
        final int height = view.getMeasuredHeight() / 2;
        final int radius = Math.max(view.getWidth(), view.getHeight()) / 2;
        final Animator animator = ViewAnimationUtils.createCircularReveal(view, width, height, 0, radius);
        // animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium));
        view.setVisibility(View.VISIBLE);
        animator.start();
    }

    public static void revealOff(@NonNull final View view) {
        if (AndroidHelper.api() < AndroidHelper.API_21) {
            view.setVisibility(View.INVISIBLE);
            return;
        }
        revealOff21(view);
    }

    @TargetApi(AndroidHelper.API_21)
    private static void revealOff21(@NonNull final View view) {
        final int width = view.getMeasuredWidth() / 2;
        final int height = view.getMeasuredHeight() / 2;
        final int radius = view.getWidth() / 2;
        final Animator animator = ViewAnimationUtils.createCircularReveal(view, width, height, radius, 0);
        // animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        animator.start();
    }

}
