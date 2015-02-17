package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

public class ActivityTransitionHelper {

    public static boolean tag(@NonNull final View view, @NonNull final String transitionName) {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            tag21(view, transitionName);
            return true;
        }
        return false;
    }

    @TargetApi(AndroidHelper.API_21)
    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    private static void tag21(@NonNull final View view, @NonNull final String transitionName) {
        view.setTransitionName(transitionName);
    }

    @SafeVarargs
    public static void transition(@NonNull final Activity activity, @NonNull final Intent intent, @NonNull final Pair<View, String>... pairs) {
        final ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle());
    }

}
