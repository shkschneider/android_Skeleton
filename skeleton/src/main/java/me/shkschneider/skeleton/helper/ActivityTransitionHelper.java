package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;

public class ActivityTransitionHelper {

    public static void tag(@NonNull final View view, @NonNull final String transitionName) {
        ViewCompat.setTransitionName(view, transitionName);
    }

    @SafeVarargs
    public static void transition(@NonNull final Activity activity, @NonNull final Intent intent, @NonNull final Pair<View, String>... pairs) {
        final ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle());
    }

}
