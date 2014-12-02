package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

// <http://developer.android.com/distribute/tools/promote/linking.html>
public class MarketHelper {

    public static enum Collection {
        FEATURED,
        EDITORS_CHOICE,
        TOP_PAID,
        TOP_FREE,
        TOP_NEW_FREE,
        TOPG_NEW_PAID,
        TOP_GROSSING,
        TRENDING,
        BEST_SELLING_IN_GAMES
    }

    public static boolean application(@NonNull final Activity activity) {
        return application(activity, ApplicationHelper.packageName());
    }

    public static boolean application(@NonNull final Activity activity, @NonNull final String pkg) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pkg));
        activity.startActivity(intent);
        return true;
    }

    public static boolean publisher(@NonNull final Activity activity, @NonNull final String pub) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?pub:" + pub));
        activity.startActivity(intent);
        return true;
    }

    public static boolean search(@NonNull final Activity activity, @NonNull final String q) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q=" + q));
        activity.startActivity(intent);
        return true;
    }

    public static boolean collection(@NonNull final Activity activity, @NonNull Collection collection) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://apps/collection/" + collection));
        activity.startActivity(intent);
        return true;
    }

}
